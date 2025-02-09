package org.greatreads.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSimpleResponseDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
