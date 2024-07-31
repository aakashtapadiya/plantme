package com.ecommerce.plantme.payloads;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
