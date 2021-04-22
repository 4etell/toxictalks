package com.etell.toxictalks.controller;

import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.TopicDtoReq;
import com.etell.toxictalks.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("topics")
public class TopicController {

    private final ChatService chatService;

    @Autowired
    public TopicController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("createTopic")
    public void createTopic(@AuthenticationPrincipal User user,
                            @RequestBody TopicDtoReq topic,
                            HttpServletResponse response) {

        if (chatService.createTopic(topic, user)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }


    @PostMapping("addParticipant")
    public void addParticipant(@AuthenticationPrincipal User user,
                               @RequestBody Long chatId,
                               HttpServletResponse response) {

        if (chatService.addParticipant(chatId, user)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @PostMapping("deleteTopic")
    public void deleteTopic(@AuthenticationPrincipal User user,
                            @RequestBody Long topicId,
                            HttpServletResponse response) {

        if (chatService.deleteTopic(topicId, user)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }



}