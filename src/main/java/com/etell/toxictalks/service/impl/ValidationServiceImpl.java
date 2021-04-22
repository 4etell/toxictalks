package com.etell.toxictalks.service.impl;

import com.etell.toxictalks.repo.UserRepo;
import com.etell.toxictalks.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final UserRepo userRepo;

    @Autowired
    public ValidationServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Map<String, String> regFormValidation(Map<String, String> regForm) {

        Map<String, String> errorMap = new HashMap<>();

        final String emptyInputError = "Поле не может быть пустым";
        final String usernameError = "usernameError";
        final String passwordError = "passwordError";
        final String emailError = "emailError";

        final String username = regForm.get("username");
        final String email = regForm.get("email");
        final String password = regForm.get("password");

        if (username.equals("")) {
            errorMap.put(usernameError, emptyInputError);
        } else if (!validateUsername(username)) {
            errorMap.put(usernameError, "Имя пользователя может состоять только из латинских букв и цифр, " +
                    "длиной от 2 до 20 символов");
        } else if (userRepo.findByUsername(username) != null) {
            errorMap.put(usernameError, "Пользователь с таким именем уже существует");
        }

        if (email.equals("")) {
            errorMap.put(emailError, emptyInputError);
        } else if (email.length() > 300) {
            errorMap.put(emailError, "Длина почты не может быть больше 300 символов");
        } else if (!validateEmail(email)) {
            errorMap.put(emailError, "Некорректно введен email");
        }

        if (password.equals("")) {
            errorMap.put(passwordError, emptyInputError);
        } else if (password.length() < 6) {
            errorMap.put(passwordError, "Минимальная длина пароля 6 символом");
        } else if (password.length() > 200) {
            errorMap.put(passwordError, "Максимальная длина пароля 200 символов");
        } else if (!password.equals(regForm.get("password2"))) {
            errorMap.put(passwordError, "Пароли не совпадают");
        }

        return errorMap;
    }

    @Override
    public Boolean chatNameValidation(String name) {
        return name != null && name.trim().length() != 0 && name.length() <= 30;
    }

    @Override
    public Boolean messageTextValidation(String text) {
        return text != null && text.trim().length() != 0 && text.length() <= 200;
    }

    @Override
    public Boolean validateEmail(String email) {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean validateUsername(String username) {
        final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
}
