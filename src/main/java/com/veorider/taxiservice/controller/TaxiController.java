package com.veorider.taxiservice.controller;

import com.veorider.taxiservice.domain.taxi.CustomerLocation;
import com.veorider.taxiservice.domain.taxi.TaxiNearby;
import com.veorider.taxiservice.domain.taxi.CustomerDetails;
import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import com.veorider.taxiservice.domain.taxi.TaxiForUpdate;
import com.veorider.taxiservice.service.TaxiService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/taxi")
public class TaxiController {

  private TaxiService taxiService;

  public TaxiController(final TaxiService taxiService) {
    this.taxiService = taxiService;
  }

  @GetMapping(value = "/all")
  public List<TaxiForDatabase> retrieveAll() {
    return taxiService.retrieveAll();
  }

  @PostMapping(value = "/persist")
  public TaxiForDatabase persist(@RequestBody final TaxiForDatabase taxi) {
    return taxiService.persist(taxi);
  }

  /* PROJECT RELATED */

  @PatchMapping("/{plateNumber}")
  public void updateTaxi(@PathVariable("plateNumber") final String plateNumber,
      @RequestBody final TaxiForUpdate taxiForUpdate) {
    taxiService.updateTaxi(plateNumber, taxiForUpdate);
  }

  @GetMapping("/{plateNumber}/request")
  public List<CustomerDetails> retrieveMostRecentCustomerRequest(@PathVariable("plateNumber")final String plateNumber) {
    return taxiService.retrieveMostRecentCustomerRequest(plateNumber);
  }

  @GetMapping("/nearby")
  public List<TaxiNearby> retrieveNearbyTaxi(@RequestBody final CustomerLocation customerLocation) {
    return taxiService.retrieveNearbyTaxi(customerLocation);
  }

}
