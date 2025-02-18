package org.greatreads.security.service.impl;

import lombok.AllArgsConstructor;
import org.greatreads.model.User;
import org.greatreads.repository.UserRepository;
import org.greatreads.security.jwt.JwtService;
import org.greatreads.security.service.UserSecurityService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return jwtService.createToken(email, user.getRole().getName());
    }
}