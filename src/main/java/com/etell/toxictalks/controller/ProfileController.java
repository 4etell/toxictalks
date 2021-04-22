package com.etell.toxictalks.controller;

import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.ProfileDtoReq;
import com.etell.toxictalks.dto.response.UserDtoRes;
import com.etell.toxictalks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("changeProfile")
    public UserDtoRes changeProfile(@AuthenticationPrincipal User user,
                                    @RequestBody ProfileDtoReq profile,
                                    HttpServletResponse response) {

        UserDtoRes userDtoRes = userService.changeProfile(profile, user);

        if (userDtoRes != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            return userDtoRes;
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

    }


}
