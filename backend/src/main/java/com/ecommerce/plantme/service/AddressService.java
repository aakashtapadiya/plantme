package com.ecommerce.plantme.service;

import com.ecommerce.plantme.payloads.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createOrUpdateAddress (Long userId, AddressDTO addressDTO);

    List<AddressDTO> getAllAddressesForUser (Long userId);

    String deleteAddressForUser (Long addressId, Long userId);
}
