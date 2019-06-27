package com.veorider.taxiservice.service;

import com.veorider.taxiservice.domain.taxi.CustomerDetails;
import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import com.veorider.taxiservice.domain.taxi.TaxiForUpdate;
import com.veorider.taxiservice.repository.TaxiRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaxiService {

  private TaxiRepository taxiRepository;

  public TaxiService(TaxiRepository taxiRepository) {
    this.taxiRepository = taxiRepository;
  }

  /*
   *
   *  FOR CREATING DATA
   *
   */

  public List<TaxiForDatabase> retrieveAll() {
    return taxiRepository.findAll();
  }

  public TaxiForDatabase persist(final TaxiForDatabase taxi) {
    taxiRepository.save(taxi);
    return taxiRepository.findByPlateNumber(taxi.getPlateNumber());
  }

  /*
   *
   *  PROJECT RELATED
   *
   */

  public TaxiForDatabase updateTaxi(String plateNumber, TaxiForUpdate taxiForUpdate) {
    TaxiForDatabase taxiForDatabase = taxiRepository.findByPlateNumber(plateNumber);

    //TODO refactor so that Lat and Lng are optional
    if(taxiForDatabase.getAvailable() != null) {
      taxiForDatabase.setAvailable(taxiForUpdate.getAvailable());
    }
    if(taxiForUpdate.getLat() != null) {
      taxiForDatabase.setLat(taxiForUpdate.getLat());
    }
    if(taxiForUpdate.getLng() != null) {
      taxiForDatabase.setLng(taxiForUpdate.getLng());
    }

    taxiRepository.save(taxiForDatabase);
    return taxiRepository.findByPlateNumber(plateNumber);
  }

  /***************TODO
   *
   * Get most recent customer requests, polling by client-side every 5 seconds
   *
   * */
  public List<CustomerDetails> retrieveMostRecentCustomerRequest(final String plateNumber) {
    return null;
  }

}
