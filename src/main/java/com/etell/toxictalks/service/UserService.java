package com.etell.toxictalks.service;

import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.ProfileDtoReq;
import com.etell.toxictalks.dto.response.UserDtoRes;

import java.util.List;
import java.util.Map;

public interface UserService {

    Boolean registerUser(Map<String, String> user);

    UserDtoRes changeProfile(ProfileDtoReq profileDtoReq, User user);

    List<String> getAllUsername();

    UserDtoRes findById(Long id);

}
