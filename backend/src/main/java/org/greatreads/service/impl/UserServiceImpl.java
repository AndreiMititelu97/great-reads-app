package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.exception.UserAlreadyExistsException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Role;
import org.greatreads.model.User;
import org.greatreads.repository.UserRepository;
import org.greatreads.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User login(String email, String password) {
        return null;//TODO security
    }

    @Override
    public User register(String email, String password, Role role) {
        return null;//TODO security
    }

    @Override
    @Transactional
    public User updateEmail(String currentEmail, String newEmail) {
        if (userRepository.existsByEmail(newEmail)) {
            throw new UserAlreadyExistsException(newEmail);
        }

        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new UserNotFoundException(currentEmail));
        user.setEmail(newEmail);

        return user;
    }

    @Override
    public User updatePassword(String email, String oldPassword, String newPassword) {
        return null;//TODO security
    }

    @Override
    public User uploadProfilePicture(String email, String pictureLink) {
        return null;
    }

    @Override
    @Transactional
    public void blockUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        user.setIsBlocked(true);
        userRepository.save(user);
    }
}
