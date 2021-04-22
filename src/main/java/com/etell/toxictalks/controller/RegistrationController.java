package com.etell.toxictalks.controller;

import com.etell.toxictalks.service.UserService;
import com.etell.toxictalks.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegistrationController {

    private final UserService userService;
    private final ValidationService validationService;

    @Autowired
    public RegistrationController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @PostMapping("/registration/registerUser")
    public Map<String, String> findUser(@RequestBody Map<String, String> regForm) {
        Map<String, String> errorMap = validationService.regFormValidation(regForm);

        if (errorMap.isEmpty()) {
            if (userService.registerUser(regForm)) {
                return null;
            } else {
                errorMap.put("Error", "Something wrong with user service");
                return errorMap;
            }
        } else {
            return errorMap;
        }
    }
}
