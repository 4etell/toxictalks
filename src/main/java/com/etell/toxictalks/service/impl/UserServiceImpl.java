package com.etell.toxictalks.service.impl;

import com.etell.toxictalks.domain.Role;
import com.etell.toxictalks.domain.Status;
import com.etell.toxictalks.domain.User;
import com.etell.toxictalks.dto.request.ProfileDtoReq;
import com.etell.toxictalks.dto.response.UserDtoRes;
import com.etell.toxictalks.repo.UserRepo;
import com.etell.toxictalks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ValidationServiceImpl validationService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, ValidationServiceImpl validationService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    @Override
    public Boolean registerUser(Map<String, String> regForm) {

        final String username = regForm.get("username");
        final String email = regForm.get("email");
        final String password = regForm.get("password");

        User user = new User(username, passwordEncoder.encode(password), email);

        user.setRoles(Collections.singleton(Role.USER));
        user.setStatus(Status.ACTIVE);
        user.setCreateDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        userRepo.save(user);

        return true;

    }

    @Override
    public UserDtoRes changeProfile(ProfileDtoReq profileDtoReq, User user) {

        String email = profileDtoReq.getEmail();
        String password = profileDtoReq.getPassword();
        int countChanges = 0;

        Optional<User> userOptional = userRepo.findById(user.getId());

        if (userOptional.isPresent()) {
            User userFromDb = userOptional.get();

            if ((!passwordEncoder.encode(password).equals(userFromDb.getPassword()))
                    && password.length() >= 6
                    && password.length() <= 200
            ) {
                userFromDb.setPassword(passwordEncoder.encode(password));
                countChanges++;
            }

            if ((!email.equals(userFromDb.getEmail())) &&
                    validationService.validateEmail(email)
                    && email.length() <= 300) {
                userFromDb.setEmail(email);
                countChanges++;
            }

            if (countChanges != 0) {
                User updatedUser = userRepo.save(userFromDb);

                return convertToUserDtoRes(updatedUser);

            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<String> getAllUsername() {
        List<User> usersList = userRepo.findAll();
        List<String> usernamesList = new ArrayList<>();
        for (User user : usersList) {
            usernamesList.add(user.getUsername());
        }
        return usernamesList;
    }

    @Override
    public UserDtoRes findById(Long id) {

        return userRepo.findById(id).map(this::convertToUserDtoRes).orElse(null);
    }

    private UserDtoRes convertToUserDtoRes(User user) {

        UserDtoRes userDtoRes = new UserDtoRes();

        userDtoRes.setId(user.getId());
        userDtoRes.setEmail(user.getEmail());
        userDtoRes.setStatus(user.getStatus());
        userDtoRes.setUsername(user.getUsername());

        return userDtoRes;

    }

}
