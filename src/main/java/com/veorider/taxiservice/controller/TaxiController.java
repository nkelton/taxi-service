package com.veorider.taxiservice.controller;

import com.veorider.taxiservice.domain.taxi.CustomerLocation;
import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import com.veorider.taxiservice.domain.taxi.request.TaxiForUpdate;
import com.veorider.taxiservice.domain.taxi.response.Customer;
import com.veorider.taxiservice.domain.taxi.response.TaxiNearby;
import com.veorider.taxiservice.service.TaxiService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/taxi")
@Slf4j
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
  public void persist(@RequestBody final List<TaxiForDatabase> taxis) {
    taxiService.persist(taxis);
  }

  @PatchMapping("/{plateNumber}")
  public void updateTaxi(@PathVariable("plateNumber") final String plateNumber,
      @RequestBody final TaxiForUpdate taxiForUpdate) {
    taxiService.updateTaxi(plateNumber, taxiForUpdate);
  }

  @GetMapping("/{plateNumber}/request")
  public DeferredResult<List<Customer>> retrieveMostRecentCustomerRequest(@PathVariable("plateNumber")final String plateNumber) {
    Long timeOutInMilliSec = 5000L;
    String timeOutResp = "Request has timed out!";
    DeferredResult<List<Customer>> deferredResult = new DeferredResult<>(timeOutInMilliSec,timeOutResp);
    CompletableFuture.runAsync(()->{
      deferredResult.setResult(taxiService.retrieveMostRecentCustomerRequest(plateNumber));
    });
    return deferredResult;
  }

  @GetMapping("/nearby")
  public List<TaxiNearby> retrieveNearbyTaxi(@RequestBody final CustomerLocation customerLocation) {
    return taxiService.retrieveNearbyTaxi(customerLocation);
  }

}
