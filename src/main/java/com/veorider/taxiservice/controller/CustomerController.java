package com.veorider.taxiservice.controller;

import com.veorider.taxiservice.domain.customer.AvailableTaxis;
import com.veorider.taxiservice.domain.customer.TaxiRequest;
import com.veorider.taxiservice.domain.customer.TaxiNearby;
import com.veorider.taxiservice.service.CustomerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/nearby")
  public List<TaxiNearby> retrieveNearbyTaxi(@RequestBody final AvailableTaxis availableTaxis) {
    return customerService.retrieveNearbyTaxi(availableTaxis);
  }

  @PostMapping("/{phoneNumber}/request")
  public String requestTaxi(@PathVariable("phoneNumber") final String phoneNumber,
      @RequestBody final TaxiRequest taxiRequest) {
    return customerService.requestTaxi(phoneNumber, taxiRequest);
  }

}
