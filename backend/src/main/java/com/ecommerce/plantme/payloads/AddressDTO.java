package com.ecommerce.plantme.payloads;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long addressId;
    private String houseBuilding;
    private String streetArea;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String addressType;

}
