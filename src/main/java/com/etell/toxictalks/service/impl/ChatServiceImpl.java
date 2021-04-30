package com.etell.toxictalks.service.impl;

import com.etell.toxictalks.domain.Chat;
import com.etell.toxictalks.domain.ChatMessage;
import com.etell.toxictalks.domain.Status;
import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.ChatMessageDtoReq;
import com.etell.toxictalks.dto.request.TopicDtoReq;
import com.etell.toxictalks.dto.response.ChatMessageDtoRes;
import com.etell.toxictalks.dto.response.TopicDtoRes;
import com.etell.toxictalks.dto.response.UserChatDtoRes;
import com.etell.toxictalks.dto.ws.WsEventDto;
import com.etell.toxictalks.dto.ws.WsEventType;
import com.etell.toxictalks.dto.ws.WsObjectType;
import com.etell.toxictalks.projections.ChatMessageProjection;
import com.etell.toxictalks.projections.ChatProjection;
import com.etell.toxictalks.projections.repo.ChatMessageProjectionRepo;
import com.etell.toxictalks.repo.ChatMessageRepo;
import com.etell.toxictalks.repo.ChatRepo;
import com.etell.toxictalks.service.ChatService;
import com.etell.toxictalks.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepo chatRepo;
    private final ChatMessageRepo chatMessageRepo;
    private final ValidationService validationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatServiceImpl(ChatRepo chatRepo,
                           ChatMessageRepo chatMessageRepo,
                           ValidationService validationService,
                           SimpMessagingTemplate simpMessagingTemplate) {
        this.chatRepo = chatRepo;
        this.chatMessageRepo = chatMessageRepo;
        this.validationService = validationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public Boolean createTopic(TopicDtoReq topicDtoReq, User user) {

        if (chatRepo.findUserChatsAndTopics(user.getId()).size() < 5) {

            if (validationService.chatNameValidation(topicDtoReq.getName())) {

                Boolean reqAttitude = topicDtoReq.getAttitude();
                String reqName = topicDtoReq.getName();
                String currentName = reqName.substring(0, 1).toUpperCase() + reqName.substring(1).toLowerCase();

                Chat chat = new Chat();

                chat.setAttitude(reqAttitude);
                chat.setName(currentName);
                chat.setCreator(user);
                chat.setStatus(Status.ACTIVE);
                chat.setCreateDate(LocalDateTime.now());
                chat.setUpdateDate(LocalDateTime.now());

                Chat updatedChat = chatRepo.save(chat);

                TopicDtoRes topicDtoRes = convertToTopicDtoRes(updatedChat);

                simpMessagingTemplate.convertAndSend("/topic/chattopics",
                        new WsEventDto<TopicDtoRes>(
                                WsObjectType.TOPIC, WsEventType.CREATE, topicDtoRes));

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public Boolean addParticipant(Long chatId, User user) {

        if (chatRepo.findUserChatsAndTopics(user.getId()).size() < 5) {

            Optional<Chat> optionalChat = chatRepo.findById(chatId);

            if (optionalChat.isPresent()) {

                Chat chatFromDb = optionalChat.get();

                if (chatFromDb.getParticipant() == null &&
                        !isUserCreator(user.getId(), chatFromDb.getCreator_id()) &&
                        chatFromDb.getStatus().equals(Status.ACTIVE)) {

                    chatFromDb.setParticipant(user);

                    Chat updatedChat = chatRepo.save(chatFromDb);

                    UserChatDtoRes userChatDtoRes = convertToUserChatDtoRes(updatedChat);

                    simpMessagingTemplate.convertAndSend("/topic/chattopics",
                            new WsEventDto<UserChatDtoRes>(
                                    WsObjectType.TOPIC, WsEventType.ADD_PARTICIPANT, userChatDtoRes));

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Boolean createMessage(ChatMessageDtoReq chatMessageDtoReq, User user) {

        if (validationService.messageTextValidation(chatMessageDtoReq.getText())) {

            Optional<Chat> optionalChat = chatRepo.findById(chatMessageDtoReq.getChatId());

            if (optionalChat.isPresent()) {

                Chat chatFromDb = optionalChat.get();

                if ((isUserCreator(user.getId(), chatFromDb.getCreator_id()) ||
                        isUserParticipant(user.getId(), chatFromDb.getParticipant_id())) &&
                        chatFromDb.getStatus().equals(Status.ACTIVE)) {

                    ChatMessage chatMessage = new ChatMessage();

                    chatMessage.setChat(chatFromDb);
                    chatMessage.setAuthor(user);
                    chatMessage.setStatus(Status.ACTIVE);
                    chatMessage.setCreateDate(LocalDateTime.now());
                    chatMessage.setUpdateDate(LocalDateTime.now());
                    chatMessage.setText(chatMessageDtoReq.getText());

                    ChatMessage updatedChatMessage = chatMessageRepo.save(chatMessage);

                    ChatMessageDtoRes chatMessageDtoRes = convertToChatMessageDtoRes(updatedChatMessage);

                    simpMessagingTemplate.convertAndSend("/topic/chat/" + chatFromDb.getCreator_id(),
                            new WsEventDto<ChatMessageDtoRes>(
                                    WsObjectType.CHAT_MESSAGE,
                                    WsEventType.CREATE,
                                    chatMessageDtoRes));

                    simpMessagingTemplate.convertAndSend("/topic/chat/" + chatFromDb.getParticipant_id(),
                            new WsEventDto<ChatMessageDtoRes>(
                                    WsObjectType.CHAT_MESSAGE,
                                    WsEventType.CREATE,
                                    chatMessageDtoRes));

                    return true;

                } else {
                    return false;
                }

            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public Boolean setReadMessages(Long chatId, User user) {

        Optional<Chat> optionalChat = chatRepo.findById(chatId);

        if (optionalChat.isPresent()) {
            Chat chatFromDb = optionalChat.get();

            if (isUserCreator(user.getId(), chatFromDb.getCreator_id()) ||
                    isUserParticipant(user.getId(), chatFromDb.getParticipant_id())) {

                List<ChatMessage> chatMessages =
                        chatMessageRepo.findAllNotUserUnreadMessages(chatFromDb.getId(), user.getId());

                for (ChatMessage message : chatMessages) {
                    message.setRead(true);
                    chatMessageRepo.save(message);
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteTopic(Long topicId, User user) {

        Optional<Chat> chatOptional = chatRepo.findById(topicId);

        if (chatOptional.isPresent()) {
            Chat chatFromDb = chatOptional.get();

            if (isUserCreator(user.getId(), chatFromDb.getCreator_id())) {

                TopicDtoRes topicDtoRes = convertToTopicDtoRes(chatFromDb);

                chatRepo.delete(chatFromDb);

                simpMessagingTemplate.convertAndSend("/topic/chattopics",
                        new WsEventDto<TopicDtoRes>(WsObjectType.TOPIC, WsEventType.REMOVE, topicDtoRes));

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public Boolean leaveChat(Long chatId, User user) {

        Optional<Chat> chatOptional = chatRepo.findById(chatId);

        if (chatOptional.isPresent()) {
            Chat chatFromDb = chatOptional.get();

            if (isUserCreator(user.getId(), chatFromDb.getCreator_id())) {

                Long chatIdFromDb = chatFromDb.getId();

                if (chatFromDb.getStatus().equals(Status.NOT_ACTIVE)) {
                    deleteChat(chatFromDb);
                } else {
                    chatFromDb.setCreator(null);
                    chatFromDb.setStatus(Status.NOT_ACTIVE);
                    Chat updatedChat = chatRepo.save(chatFromDb);

                    UserChatDtoRes userChatDtoRes = convertToUserChatDtoRes(updatedChat);

                    simpMessagingTemplate.convertAndSend("/topic/chat/" + chatFromDb.getParticipant_id(),
                            new WsEventDto<UserChatDtoRes>(
                                    WsObjectType.CHAT,
                                    WsEventType.SET_STATUS_NOT_ACTIVE, userChatDtoRes));

                }

                simpMessagingTemplate.convertAndSend("/topic/chat/" + user.getId(),
                        new WsEventDto<Long>(
                                WsObjectType.CHAT,
                                WsEventType.REMOVE, chatFromDb.getId()));

                return true;
            } else if (isUserParticipant(user.getId(), chatFromDb.getParticipant_id())) {

                if (chatFromDb.getStatus().equals(Status.NOT_ACTIVE)) {
                    deleteChat(chatFromDb);
                } else {
                    chatFromDb.setParticipant(null);
                    chatFromDb.setStatus(Status.NOT_ACTIVE);
                    Chat updatedChat = chatRepo.save(chatFromDb);

                    UserChatDtoRes userChatDtoRes = convertToUserChatDtoRes(updatedChat);

                    simpMessagingTemplate.convertAndSend("/topic/chat/" + chatFromDb.getCreator_id(),
                            new WsEventDto<UserChatDtoRes>(
                                    WsObjectType.CHAT,
                                    WsEventType.SET_STATUS_NOT_ACTIVE, userChatDtoRes));
                }

                simpMessagingTemplate.convertAndSend("/topic/chat/" + user.getId(),
                        new WsEventDto<Long>(
                                WsObjectType.CHAT,
                                WsEventType.REMOVE, chatFromDb.getId()));

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<TopicDtoRes> findAllTopics(Long userId) {
        return chatRepo.
                findAllTopics(userId)
                .stream()
                .map(this::convertToTopicDtoRes)
                .collect(Collectors.toList());
    }

    @Override
    public List<TopicDtoRes> findUserTopics(Long userId) {
        return chatRepo.
                findUserTopics(userId)
                .stream()
                .map(this::convertToTopicDtoRes)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserChatDtoRes> findUserChats(Long userId) {
        return chatRepo
                .findUserChats(userId)
                .stream()
                .map(this::convertToUserChatDtoRes)
                .collect(Collectors.toList());
    }


    private UserChatDtoRes convertToUserChatDtoRes(ChatProjection chat) {

        UserChatDtoRes userChatDtoRes = new UserChatDtoRes();

        userChatDtoRes.setId(chat.getId());
        userChatDtoRes.setAttitude(chat.getAttitude());
        userChatDtoRes.setName(chat.getName());
        userChatDtoRes.setCreatorId(chat.getCreator_id());
        userChatDtoRes.setParticipantId(chat.getParticipant_id());
        userChatDtoRes.setStatus(chat.getStatus());

        List<ChatMessageProjectionRepo> messagesFromDb = chatMessageRepo.findChatMessages(chat.getId());
        if (messagesFromDb != null) {
            userChatDtoRes.setMessages(messagesFromDb
                    .stream()
                    .map(this::convertToChatMessageDtoRes)
                    .collect(Collectors.toList()));
        }

        return userChatDtoRes;
    }

    private ChatMessageDtoRes convertToChatMessageDtoRes(ChatMessageProjection message) {

        ChatMessageDtoRes chatMessageDtoRes = new ChatMessageDtoRes();

        chatMessageDtoRes.setId(message.getId());
        chatMessageDtoRes.setText(message.getText());
        chatMessageDtoRes.setUpdateDate(message.getUpdate_date());
        chatMessageDtoRes.setAuthorId(message.getAuthor_id());
        chatMessageDtoRes.setChatId(message.getChat_id());
        chatMessageDtoRes.setRead(message.getRead());

        return chatMessageDtoRes;
    }

    private TopicDtoRes convertToTopicDtoRes(ChatProjection chat) {

        TopicDtoRes topicDtoRes = new TopicDtoRes();

        topicDtoRes.setId(chat.getId());
        topicDtoRes.setAttitude(chat.getAttitude());
        topicDtoRes.setName(chat.getName());
        topicDtoRes.setCreatorId(chat.getCreator_id());
        if (chat.getParticipant_id() != null) topicDtoRes.setParticipantId(chat.getParticipant_id());

        return topicDtoRes;
    }

    private boolean isUserCreator(Long userId, Long creatorId) {
        return userId.equals(creatorId);
    }

    private boolean isUserParticipant(Long userId, Long participantId) {
        return userId.equals(participantId);
    }

    private void deleteChat(Chat chat) {

        List<ChatMessage> messageList = chatMessageRepo.findAllByChatId(chat.getId());

        for (ChatMessage message : messageList) {
            chatMessageRepo.delete(message);
        }

        chatRepo.delete(chat);

    }
}
