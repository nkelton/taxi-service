package com.veorider.taxiservice.repository;

import com.veorider.taxiservice.domain.customer.CustomerForDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerForDatabase, Long> {

}
