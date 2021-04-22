package com.etell.toxictalks.controller;

import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.ChatMessageDtoReq;
import com.etell.toxictalks.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("createMessage")
    public void createMessage(@AuthenticationPrincipal User user,
                              @RequestBody ChatMessageDtoReq message,
                              HttpServletResponse response) {

        if (chatService.createMessage(message, user)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("setReadMessages")
    public void setReadMessages(@AuthenticationPrincipal User user,
                                @RequestBody Long chatId,
                                HttpServletResponse response) {

        if (chatService.setReadMessages(chatId, user)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @PostMapping("leaveChat")
    public void leaveChat(@AuthenticationPrincipal User user,
                          @RequestBody Long chatId,
                          HttpServletResponse response) {

        if (chatService.leaveChat(chatId, user)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
