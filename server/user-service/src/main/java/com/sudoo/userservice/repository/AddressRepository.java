package com.sudoo.userservice.repository;

import com.sudoo.userservice.repository.entitity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
