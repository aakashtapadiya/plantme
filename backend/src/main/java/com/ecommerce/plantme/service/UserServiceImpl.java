package com.ecommerce.plantme.service;

import com.ecommerce.plantme.entity.User;
import com.ecommerce.plantme.exceptions.CommonApiException;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.UserDTO;
import com.ecommerce.plantme.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl (UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }


    /**
     * Register new User
     *
     * @param userDTO
     * @return UserDTO
     */
    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        try {
            User user = this.dtoToUser(userDTO);
            User savedUser = userRepo.save(user);
            return this.userToDto(savedUser);

        } catch (DataIntegrityViolationException e) {
            if (userRepo.findByEmail(userDTO.getEmail()) != null) {
                throw new CommonApiException("Email Id Already exists : " + userDTO.getEmail());
            }
            if (userRepo.findByMobileNumber(userDTO.getMobileNumber()) != null) {
                throw new CommonApiException("Mobile Number Already exists : " + userDTO.getMobileNumber());
            }
            throw new CommonApiException(e.getMessage());
        }

    }


    /**
     * Get User using Primary key User Id
     *
     * @param userId
     * @return userDTO
     */
    @Override
    public UserDTO getUserbyId(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty() || userId<0) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }
        return this.userToDto(user.get());
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
     * MAPPING UserDTO TO User
     * @param user
     * @return userDTO
     */
    private UserDTO userToDto (User user) {
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
