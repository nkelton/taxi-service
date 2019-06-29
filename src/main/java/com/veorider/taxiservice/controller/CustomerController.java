package com.veorider.taxiservice.controller;

import com.veorider.taxiservice.domain.customer.CustomerForDatabase;
import com.veorider.taxiservice.domain.customer.request.TaxiDecision;
import com.veorider.taxiservice.domain.customer.request.TaxiRequest;
import com.veorider.taxiservice.service.CustomerService;
import java.util.List;
import java.util.concurrent.Callable;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping(value = "/all")
  public List<CustomerForDatabase> retrieveAll() {
    return customerService.retrieveAll();
  }

  @PostMapping(value = "/persist")
  public void persist(@RequestBody final List<CustomerForDatabase> customers) {
    customerService.persist(customers);
  }

  @PatchMapping("/{phoneNumber}/request")
  public boolean decideRequest(@PathVariable("phoneNumber") final String phoneNumber,
      @RequestBody final TaxiDecision taxiDecision) {
    return customerService.decideRequest(phoneNumber, taxiDecision);
  }

  @PostMapping("/{phoneNumber}/request")
  public Callable<String> requestTaxi(@PathVariable("phoneNumber") final String phoneNumber,
      @RequestBody final TaxiRequest taxiRequest) {
    try{
      return () -> customerService.requestTaxi(phoneNumber, taxiRequest);
    } catch (Exception e) {
      return null;
    }
  }

}
