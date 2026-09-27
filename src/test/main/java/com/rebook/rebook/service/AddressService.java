package com.rebook.rebook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.User;
import com.rebook.rebook.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getUserAddresses(User user) {
        return addressRepository.findByUser(user);
    }

    public Address getLatestAddress(User user) {
        return addressRepository.findTopByUserOrderByIdDesc(user);
    }
}