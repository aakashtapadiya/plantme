package com.ecommerce.plantme.repository;

import com.ecommerce.plantme.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository <Address, Long> {

    @Query ("Select a from Address a WHERE " +
            "a.country=?1 AND " +
            "a.state=?2 AND " +
            "a.city=?3 AND " +
            "a.pincode=?4 AND " +
            "a.streetArea=?5 AND " +
            "a.houseBuilding=?6")
    Address findExistingAddress (
            String country, String state, String city, String pincode, String streetArea, String houseBuilding
    );

    @Query("SELECT a FROM Address a JOIN a.users u WHERE a.addressId = ?1 AND u.userId = ?2")
    Address findAddressByUserId (Long addressId, Long userId);

    @Query("SELECT a FROM Address a JOIN a.users u WHERE u.userId = ?1")
    List<Address> findAllAddressesByUserId (Long userId);

}
