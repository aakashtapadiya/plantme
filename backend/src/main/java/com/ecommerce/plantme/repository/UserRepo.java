package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail (String email);

    User findByMobileNumber (String mobileNumber);

}
