package com.veorider.taxiservice.repository;

import com.veorider.taxiservice.domain.customer.CustomerForDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerForDatabase, Long> {

}
