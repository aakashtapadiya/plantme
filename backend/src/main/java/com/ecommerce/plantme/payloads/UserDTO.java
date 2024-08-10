package com.ecommerce.plantme.payloads;

import com.ecommerce.plantme.entity.enums.UserStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String password;
    private UserStatus userStatus = UserStatus.INACTIVE;

}
