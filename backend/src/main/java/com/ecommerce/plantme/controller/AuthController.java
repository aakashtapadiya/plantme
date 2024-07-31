package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.payloads.UserDTO;
import com.ecommerce.plantme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private UserService userService;

//    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerHandler ( @RequestBody UserDTO theUserDTO) {

        UserDTO userDTO = userService.registerUser(theUserDTO);

        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }




}
