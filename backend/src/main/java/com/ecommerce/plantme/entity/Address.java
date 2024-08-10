package com.ecommerce.plantme.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "house_building")
    private String houseBuilding;

    @Column(name = "street_area")
    private String streetArea;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "address_type")
    private String addressType;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

}
