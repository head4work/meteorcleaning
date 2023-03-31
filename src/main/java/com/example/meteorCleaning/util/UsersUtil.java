package com.example.meteorCleaning.util;

import com.example.meteorCleaning.dto.PasswordRecoveryTo;
import com.example.meteorCleaning.dto.UserTo;
import com.example.meteorCleaning.model.Role;
import com.example.meteorCleaning.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled());
    }

    public static User updateFromTo(User user, UserTo userTo,PasswordEncoder passwordEncoder) {
        String password = userTo.getPassword();
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setName(userTo.getName());
        user.setEnabled(userTo.isEnabled());
        if (!password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return user;
    }

    public static User updateFromProfile(User user, User userTo, PasswordEncoder passwordEncoder) {
        String password = userTo.getPassword();
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setName(userTo.getName());
        if (!password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return user;
    }

    public static User updateFromForgot(User user, PasswordRecoveryTo forgotTo, PasswordEncoder passwordEncoder) {
        String password = forgotTo.getPassword();
        if (!password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }


}