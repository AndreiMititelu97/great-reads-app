package org.greatreads.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.greatreads.dto.user.UserLoginDTO;
import org.greatreads.dto.user.UserRegisterDTO;
import org.greatreads.dto.user.UserSimpleResponseDTO;
import org.greatreads.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8082")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userService.authenticate(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserSimpleResponseDTO> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRegisterDTO));
    }

    //TODO updatePassword
    //TODO updateemail
    //TODO add profile picture
}
