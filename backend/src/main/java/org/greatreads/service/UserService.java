package org.greatreads.service;

import org.greatreads.model.Role;
import org.greatreads.model.User;

public interface UserService {
    User login(String email, String password);
    User register(String email, String password, Role role);
    User updateEmail(String currentEmail, String newEmail);
    User updatePassword(String email, String oldPassword, String newPassword);
    User uploadProfilePicture(String email, String pictureLink);//TODO
    void blockUser(String email);
}
