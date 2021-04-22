package com.etell.toxictalks.service;

import java.util.Map;

public interface ValidationService {

    Map<String, String> regFormValidation(Map<String, String> regForm);

    Boolean chatNameValidation(String name);

    Boolean messageTextValidation(String text);

    Boolean validateEmail(String email);

}
