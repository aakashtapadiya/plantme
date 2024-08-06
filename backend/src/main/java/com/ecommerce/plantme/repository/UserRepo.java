package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("Select u from User u WHERE u.email = ?1")
    User findByEmail (String email);

    @Query("Select u from User u WHERE u.mobileNumber = ?1")
    User findByMobileNumber (String mobileNumber);

}
