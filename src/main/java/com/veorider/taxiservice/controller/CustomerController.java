package com.veorider.taxiservice.controller;

import com.veorider.taxiservice.domain.customer.TaxiDecision;
import com.veorider.taxiservice.domain.customer.TaxiRequest;
import com.veorider.taxiservice.service.CustomerService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {
  private CustomerService customerService;

  public CustomerController(final CustomerService customerService) {
    this.customerService = customerService;
  }

  @PatchMapping("/{phoneNumber}/request")
  public boolean decideRequest(@PathVariable("phoneNumber") final String phoneNumber,
      @RequestBody final TaxiDecision taxiDecision) {
    return customerService.decideRequest(phoneNumber, taxiDecision);
  }

  @PostMapping("/{phoneNumber}/request")
  public String requestTaxi(@PathVariable("phoneNumber") final String phoneNumber,
      @RequestBody final TaxiRequest taxiRequest) {
    return customerService.requestTaxi(phoneNumber, taxiRequest);
  }



}
