package com.rebook.rebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rebook.rebook.entity.Address;
import com.rebook.rebook.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

    Address findTopByUserOrderByIdDesc(User user);

}