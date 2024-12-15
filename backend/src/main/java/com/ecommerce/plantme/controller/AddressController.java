package com.ecommerce.plantme.controller;

import com.ecommerce.plantme.payloads.AddressDTO;
import com.ecommerce.plantme.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    //Injecting
    private AddressService addressService;

    @Autowired
    public AddressController (AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address/{userId}")
    public ResponseEntity<AddressDTO> createOrUpdateAddress (@PathVariable Long userId, @RequestBody AddressDTO addressDTO) {
        AddressDTO savedAddressDTO = addressService.createOrUpdateAddress(userId, addressDTO);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/address/{userId}")
    public ResponseEntity<List<AddressDTO>> getAddresses (@PathVariable Long userId) {
        List<AddressDTO> addressDTOS = addressService.getAllAddressesForUser(userId);
        return new ResponseEntity<>(addressDTOS, HttpStatus.FOUND);
    }

    @DeleteMapping("/address/{addressId}/{userId}")
    public ResponseEntity<String> deleteAddress (@PathVariable Long addressId, @PathVariable Long userId) {
        String status = addressService.deleteAddressForUser(addressId, userId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
