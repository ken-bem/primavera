package com.kenbem.primavera.user.service;

import com.kenbem.primavera.models.user.User;
import com.kenbem.primavera.models.user.UserRepository;
import com.kenbem.primavera.user.UserDto;
import com.kenbem.primavera.user.exception.EmailAlreadyUsedException;
import com.kenbem.primavera.user.exception.UsernameAlreadyUsedException;
import com.kenbem.primavera.utils.security.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional
                .ofNullable(userRepository.findByUsername(username))
                .orElse(Optional.empty());
    }

    @Override
    public User registerUser(User test, String password) {

        userRepository
                .findByUsername(test.getUsername())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if(!removed){
                        throw new UsernameAlreadyUsedException();
                    }
                });

        userRepository.findByEmail(test.getEmail())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                       throw new EmailAlreadyUsedException();
                    }
        });


        //TODO: Set all user details
        return null;
    }

    @Override
    public Optional<UserDto> updateUser(UserDto userDto) {
        return Optional
                .of(userRepository.findById(userDto.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(userDto.getEmail()));
                    return user;
                })
                .map(UserDto::new);
    }

    @Override
    public void deleteUser(User user) {
        userRepository
                .findById(user.getId())
                .ifPresent(userRepository::delete);
    }

    @Override
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
                .getCurrentUser()
                .flatMap(user -> userRepository.findById(user.getId()))
                .ifPresent(user ->{
                    String currentEncryptedPassword = user.getPassword();
                    if(!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)){
                        //Todo: throw invalid password exception;
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    userRepository.save(user);
                });
    }

    @Override
    public User createUser(UserDto userDto) {

        //TODO
        return null;
    }


    private boolean removeNonActivatedUser(User user){
        if (user.isActivated()){
            return false;
        }

        userRepository.delete(user);
        userRepository.flush();
        return true;
    }
}
