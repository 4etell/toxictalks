package com.etell.toxictalks.controller;

import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.service.ChatService;
import com.etell.toxictalks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${spring.profiles.active:prod}")
    private String profile;

    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public MainController(UserService userService,
                          ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {

        if (user != null) {

            Long userId = user.getId();

            model.addAttribute("profile", userService.findById(userId));
            model.addAttribute("userTopics", chatService.findUserTopics(userId));
            model.addAttribute("allTopics", chatService.findAllTopics(userId));
            model.addAttribute("userChats", chatService.findUserChats(userId));
        } else {
            model.addAttribute("profile", null);
        }

        model.addAttribute("usernamesList", userService.getAllUsername());
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "main";
    }
}
