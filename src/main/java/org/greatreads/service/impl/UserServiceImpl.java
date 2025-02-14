package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.user.UserSimpleResponseDTO;
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
    public void login(String email, String password) {
        //TODO security
    }

    @Override
    public UserSimpleResponseDTO register(String email, String password, Role role) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        userRepository.save(user);

        return userToDto(user);
    }

    @Override
    @Transactional
    public UserSimpleResponseDTO updateEmail(String currentEmail, String newEmail) {
        if (userRepository.existsByEmail(newEmail)) {
            throw new UserAlreadyExistsException(newEmail);
        }

        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new UserNotFoundException(currentEmail));
        user.setEmail(newEmail);
        userRepository.save(user);

        return userToDto(user);
    }

    @Override
    public UserSimpleResponseDTO updatePassword(String email, String oldPassword, String newPassword) {
        return null;//TODO security
    }

    @Override
    public UserSimpleResponseDTO uploadProfilePicture(String email, String pictureLink) {
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

    private UserSimpleResponseDTO userToDto(User user) {
        UserSimpleResponseDTO userResponseDTO = new UserSimpleResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setAvatar(user.getAvatar());
        return userResponseDTO;
    }
}
