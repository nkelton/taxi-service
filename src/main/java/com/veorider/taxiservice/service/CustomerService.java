package com.veorider.taxiservice.service;

import com.veorider.taxiservice.domain.customer.TaxiDecision;
import com.veorider.taxiservice.domain.customer.TaxiRequest;
import com.veorider.taxiservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public boolean decideRequest(String phoneNumber, TaxiDecision taxiDecision){
    return false;
  }

  public String requestTaxi(String phoneNumber, TaxiRequest taxiRequest){
    return null;
  }
}
