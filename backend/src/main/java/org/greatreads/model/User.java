package org.greatreads.model;

import lombok.Data;
import org.greatreads.util.Role;

@Data
public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String avatar;
    private boolean isBlocked;
    //TODO Add hibernate, foreign key?
}
