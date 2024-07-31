package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.UserDTO;


public interface UserService {

    UserDTO registerUser (UserDTO user);

    UserDTO getUserbyId (Long userId);

}
