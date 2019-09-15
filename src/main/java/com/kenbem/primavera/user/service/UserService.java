package com.kenbem.primavera.user.service;

import com.kenbem.primavera.models.user.User;
import com.kenbem.primavera.user.UserDto;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByUsername(String username);

    User registerUser(User user, String password);

    Optional<UserDto> updateUser(UserDto userDto);

    void deleteUser(User user);

    void changePassword(String currentClearTextPassword, String newPassword);

    User createUser(UserDto userDto);

}
