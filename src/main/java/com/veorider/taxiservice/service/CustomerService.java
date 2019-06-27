package com.veorider.taxiservice.service;

import com.veorider.taxiservice.domain.customer.AvailableTaxis;
import com.veorider.taxiservice.domain.customer.TaxiNearby;
import com.veorider.taxiservice.domain.customer.TaxiRequest;
import com.veorider.taxiservice.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<TaxiNearby> retrieveNearbyTaxi(AvailableTaxis availableTaxis) {
    return null;
  }

  public String requestTaxi(String phoneNumber, TaxiRequest taxiRequest){
    return null;
  }

}
