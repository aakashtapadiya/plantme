package com.ecommerce.plantme.implementation;

import com.ecommerce.plantme.entity.User;
import com.ecommerce.plantme.exceptions.CommonApiException;
import com.ecommerce.plantme.exceptions.ResourceAlreadyFoundException;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.UserDTO;
import com.ecommerce.plantme.repository.UserRepo;
import com.ecommerce.plantme.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private ModelMapper modelMapper;



    @Autowired
    public UserServiceImpl (
            UserRepo userRepo,
            ModelMapper modelMapper
    ) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    /**
     * Register new User
     *
     * @param userDTO
     * @return UserDTO
     */
    @Transactional
    @Override
    public UserDTO registerUser (UserDTO userDTO)  {

        if (userRepo.findByEmail(userDTO.getEmail()) != null) {
            throw new ResourceAlreadyFoundException("Email Id","email id",userDTO.getEmail());
        }
        if (userRepo.findByMobileNumber(userDTO.getMobileNumber()) != null) {
            throw new ResourceAlreadyFoundException("Mobile Number", "mobile number",userDTO.getMobileNumber());
        }
        User user = this.dtoToUser(userDTO);
        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    /**
     * Get User using Primary key User Id
     *
     * @param userId
     * @return userDTO
     */

    @Override
    public UserDTO getUserbyId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        return this.userToDto(user);
    }

    /**
     * MAPPING UserDTO to User
     * @param userDTO
     * @return User
     */
    private User dtoToUser (UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        return user;
    }

    /**
     * MAPPING user TO UserDTO
     * @param user
     * @return userDTO
     */
    private UserDTO userToDto (User user) {
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
