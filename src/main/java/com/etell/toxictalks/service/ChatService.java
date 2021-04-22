package com.etell.toxictalks.service;

import com.etell.toxictalks.domain.Chat;
import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.ChatMessageDtoReq;
import com.etell.toxictalks.dto.request.TopicDtoReq;
import com.etell.toxictalks.dto.response.TopicDtoRes;
import com.etell.toxictalks.dto.response.UserChatDtoRes;

import java.util.List;

public interface ChatService {

    Boolean createTopic(TopicDtoReq topic, User user);

    Boolean addParticipant (Long chatId, User user);

    Boolean createMessage(ChatMessageDtoReq message, User user);

    Boolean setReadMessages(Long chatId, User user);

    Boolean deleteTopic(Long topicId, User user);

    Boolean leaveChat(Long chatId, User user);

    List<TopicDtoRes> findUserTopics(Long userId);

    List<TopicDtoRes> findAllTopics(Long userId);

    List<UserChatDtoRes> findUserChats(Long userId);
}
