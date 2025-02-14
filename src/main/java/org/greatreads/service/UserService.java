package org.greatreads.service;

import org.greatreads.dto.user.UserSimpleResponseDTO;
import org.greatreads.model.Role;

public interface UserService {
    void login(String email, String password);
    UserSimpleResponseDTO register(String email, String password, Role role);
    UserSimpleResponseDTO updateEmail(String currentEmail, String newEmail);
    UserSimpleResponseDTO updatePassword(String email, String oldPassword, String newPassword);
    UserSimpleResponseDTO uploadProfilePicture(String email, String pictureLink);
    void blockUser(String email);
}