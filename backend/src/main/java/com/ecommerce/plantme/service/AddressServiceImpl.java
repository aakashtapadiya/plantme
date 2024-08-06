package com.ecommerce.plantme.service;

import com.ecommerce.plantme.entity.Address;
import com.ecommerce.plantme.entity.User;
import com.ecommerce.plantme.exceptions.CommonApiException;
import com.ecommerce.plantme.exceptions.ResourceNotFoundException;
import com.ecommerce.plantme.payloads.AddressDTO;
import com.ecommerce.plantme.repository.AddressRepo;
import com.ecommerce.plantme.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepo addressRepo;

    private UserRepo userRepo;

    private ModelMapper modelMapper;


    @Autowired
    public AddressServiceImpl (
            AddressRepo addressRepo,
            UserRepo userRepo,
            ModelMapper modelMapper
    ) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    /**
     * Create or Update an Address for Particular User
     *
     * @param userId
     * @param addressDTO
     * @return AddressDTO
     */
    @Transactional
    @Override
    public AddressDTO createOrUpdateAddress (Long userId, AddressDTO addressDTO) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        String country = addressDTO.getCountry();
        String state = addressDTO.getState();
        String city = addressDTO.getCity();
        String pincode = addressDTO.getPincode();
        String streetArea = addressDTO.getStreetArea();
        String houseBuilding = addressDTO.getHouseBuilding();

        Address addressDB = addressRepo.findExistingAddress (
                country, state, city, pincode, streetArea, houseBuilding
        );

        if (addressDB != null){
            Long addressIdFromDb = addressDB.getAddressId();
            if (addressRepo.findAddressByUserId(addressIdFromDb, userId) !=null ){
                throw new CommonApiException("Address already exists for UserId : "+ userId);
            } else {
                //it means that user has changed to an address which is already there in the database;
                //in this case we can add the dbAddress for the User
                user.getAddresses().add(addressDB);
                userRepo.save(user);
                return this.addressToDto(addressDB);
            }
        }
            Address address = this.dtoToAddress(addressDTO);
            Address savedAddress = addressRepo.save(address);

            user.getAddresses().add(savedAddress);
            userRepo.save(user);
            return this.addressToDto(savedAddress);
    }

    /**
     * GET All Addresses for a User
     *
     * @param userId
     * @return List<AddressDTO>
     */
    @Override
    public List<AddressDTO> getAllAddressesForUser (Long userId) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        List<Address> addresses = addressRepo.findAllAddressesByUserId(userId);
        if (addresses == null) {
            throw new ResourceNotFoundException("Addresses", "userId", userId);
        }
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address addressDB : addresses) {
            addressDTOS.add(this.addressToDto(addressDB));
        }
        return addressDTOS;
    }

    /**
     * Delete Address for a User by UserId
     *
     * @param addressId
     * @param userId
     * @return String
     */
    @Transactional
    @Override
    public String deleteAddressForUser (Long addressId, Long userId) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        Address userAddress = addressRepo.findAddressByUserId(addressId, userId);

        if (userAddress == null) {
            throw new ResourceNotFoundException("Address", "userId", userId);
        }

        user.getAddresses().remove(userAddress);
        userRepo.save(user);

        return "Address Deleted Successfully";
    }

    /**
     * MAPPING AddressDTO to Address
     * @param addressDTO
     * @return Address
     */
    private Address dtoToAddress (AddressDTO addressDTO) {
        Address address = this.modelMapper.map(addressDTO, Address.class);
        return address;
    }

    /**
     * MAPPING Address TO AddressDTO
     * @param address
     * @return AddressDTO
     */
    private AddressDTO addressToDto (Address address) {
        AddressDTO addressDTO = this.modelMapper.map(address, AddressDTO.class);
        return addressDTO;
    }
}
