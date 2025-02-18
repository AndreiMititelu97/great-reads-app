package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.user.UserRegisterDTO;
import org.greatreads.dto.user.UserSimpleResponseDTO;
import org.greatreads.exception.UserAlreadyExistsException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Role;
import org.greatreads.model.User;
import org.greatreads.repository.RoleRepository;
import org.greatreads.repository.UserRepository;
import org.greatreads.security.jwt.JwtService;
import org.greatreads.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return jwtService.createToken(email, user.getRole().getName());
    }

    @Override
    @Transactional
    public UserSimpleResponseDTO register(UserRegisterDTO userRegisterDTO) {
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new UserAlreadyExistsException(userRegisterDTO.getEmail());
        }

        Role defaultRole = roleRepository.findByName("ROLE_READER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        user.setRole(defaultRole);
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return userToDto(user);
    }

    @Override
    public UserSimpleResponseDTO uploadProfilePicture(String email, String pictureLink) {
        return null;//TODO
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
        return userResponseDTO;
    }
}
