package com.zekaya.eccommerce.repository;

import com.zekaya.eccommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
