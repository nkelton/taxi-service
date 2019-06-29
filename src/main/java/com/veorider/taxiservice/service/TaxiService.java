package com.veorider.taxiservice.service;

import com.veorider.taxiservice.domain.customer.CustomerRequest;
import com.veorider.taxiservice.domain.taxi.CustomerLocation;
import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import com.veorider.taxiservice.domain.taxi.request.TaxiForUpdate;
import com.veorider.taxiservice.domain.taxi.response.Customer;
import com.veorider.taxiservice.domain.taxi.response.TaxiNearby;
import com.veorider.taxiservice.repository.CustomerRepository;
import com.veorider.taxiservice.repository.TaxiRepository;
import com.veorider.taxiservice.utils.DistanceUtil;
import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaxiService {

  private TaxiRepository taxiRepository;

  public TaxiService(TaxiRepository taxiRepository, CustomerRepository customerRepository) {
    this.taxiRepository = taxiRepository;
  }

  public List<TaxiForDatabase> retrieveAll() {
    return taxiRepository.findAll();
  }

  public void persist(final List<TaxiForDatabase> taxis) {
      taxis.forEach(taxi -> {
        taxiRepository.save(taxi);
      });
  }

  public void updateTaxi(String plateNumber, TaxiForUpdate taxiForUpdate) {
    Optional<TaxiForDatabase> taxiForDatabase = taxiRepository.findByPlateNumber(plateNumber);
    if(taxiForDatabase.isPresent()) {

      if(taxiForUpdate.getAvailable() == null) {
        throw new MalformedParametersException("'available' field is required");
      }
      taxiForDatabase.get().setAvailable(taxiForUpdate.getAvailable());

      if(taxiForUpdate.getLat() != null) {
        taxiForDatabase.get().setLat((taxiForUpdate.getLat()));
      }

      if(taxiForUpdate.getLng() != null) {
        taxiForDatabase.get().setLng((taxiForUpdate.getLng()));
      }
      taxiRepository.save(taxiForDatabase.get());
    }
  }

  public List<Customer> retrieveMostRecentCustomerRequest(final String plateNumber) {
    Optional<TaxiForDatabase> taxi = taxiRepository.findByPlateNumber(plateNumber);
    List<Customer> customers = new ArrayList<>();

    if(taxi.isPresent()) {
      //sort by customerRequestTime - result is ascending
      taxi.get().getRequests().sort(
          Comparator.comparing(CustomerRequest::getRequestTime)
      );

      // Generate an iterator. Start just after the last element.
      ListIterator li = taxi.get().getRequests().listIterator(taxi.get().getRequests().size());

      while(li.hasPrevious()) {
        CustomerRequest customerRequest = (CustomerRequest) li.previous();

        customers.add(
            Customer.builder()
                .phone(customerRequest.getPhoneNumber())
                .lat(customerRequest.getLat())
                .lng(customerRequest.getLng())
                .build());
      }
    }

    return customers;
  }

  public List<TaxiNearby> retrieveNearbyTaxi(CustomerLocation customerLocation) {
    List<TaxiForDatabase> availableTaxis = taxiRepository.findByAvailableTrue();
    List<TaxiNearby> nearbyTaxis = new ArrayList();

    availableTaxis.forEach(taxi -> {
      float distance = DistanceUtil.calcDistance(customerLocation.getLat(), customerLocation.getLng(),
          taxi.getLat(), taxi.getLng());
      if(distance <= customerLocation.getDistance()) {
        nearbyTaxis.add(TaxiNearby.builder()
            .plateNumber(taxi.getPlateNumber())
            .lat(taxi.getLat())
            .lng(taxi.getLng())
            .build());
      }
    });

    return nearbyTaxis;
  }
}
