package org.greatreads.service;

import org.greatreads.dto.user.UserRegisterDTO;
import org.greatreads.dto.user.UserSimpleResponseDTO;

public interface UserService {
    String authenticate(String email, String password);
    UserSimpleResponseDTO register(UserRegisterDTO userRegisterDTO);
    UserSimpleResponseDTO updateEmail(String currentEmail, String newEmail);
    UserSimpleResponseDTO updatePassword(String email, String oldPassword, String newPassword);
    UserSimpleResponseDTO uploadProfilePicture(String email, String pictureLink);
    void blockUser(String email);
}