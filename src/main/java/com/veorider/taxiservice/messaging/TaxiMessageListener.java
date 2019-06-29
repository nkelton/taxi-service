package com.veorider.taxiservice.messaging;

import com.veorider.taxiservice.config.RabbitConfig;
import com.veorider.taxiservice.domain.customer.CustomerRequest;
import com.veorider.taxiservice.domain.customer.messaging.CustomerRequestPayload;
import com.veorider.taxiservice.domain.customer.messaging.MessageType;
import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import com.veorider.taxiservice.repository.TaxiRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Transactional
@Slf4j
public class TaxiMessageListener {

  private TaxiRepository taxiRepository;

  public TaxiMessageListener(TaxiRepository taxiRepository) {
    this.taxiRepository = taxiRepository;
  }

  @RabbitListener(queues = RabbitConfig.CUSTOMER_REQUEST_QUEUE)
  public void processRequest(CustomerRequestPayload customerRequestPayload) {
    log.info("Processing new customer request: " + customerRequestPayload);
    if(customerRequestPayload.getMessageType().equals(MessageType.ACCEPTED)) {
      //update requested taxi
      Optional<TaxiForDatabase> requestedTaxi = taxiRepository.findByPlateNumber(customerRequestPayload.getRespondedPlate());
      if(requestedTaxi.isPresent()) {
        requestedTaxi.get().setAvailable(false);
        removeCustomerRequestFromTaxi(requestedTaxi.get(), customerRequestPayload.getPhoneNumber());

        //remove customer request from all other taxis
        List<TaxiForDatabase> taxis = taxiRepository.findAll();
        taxis.forEach(taxi -> removeCustomerRequestFromTaxi(taxi, customerRequestPayload.getPhoneNumber()));
      }
    }
    else if(customerRequestPayload.getMessageType().equals(MessageType.DENIED)) {
      //update requestedTaxi
      Optional<TaxiForDatabase> taxi = taxiRepository.findByPlateNumber(customerRequestPayload.getRespondedPlate());
      taxi.ifPresent(taxiForDatabase ->
          removeCustomerRequestFromTaxi(taxiForDatabase, customerRequestPayload.getPhoneNumber()));
    }
    else if(customerRequestPayload.getMessageType().equals(MessageType.REQUEST)){
      //add customer requests to database
      customerRequestPayload.getLicensePlates().forEach(requestedPlate -> {
        Optional<TaxiForDatabase> taxi = taxiRepository.findByPlateNumber(requestedPlate);
        if(taxi.isPresent()) {
          List<CustomerRequest> updatedCustomerRequests = taxi.get().getRequests();
          CustomerRequest newCustomerRequest = CustomerRequest.builder()
              .phoneNumber(customerRequestPayload.getPhoneNumber())
              .lat(customerRequestPayload.getLat())
              .lng(customerRequestPayload.getLng())
              .requestTime(LocalDateTime.now())
              .licensePlates(requestedPlate)
              .build();

          updatedCustomerRequests.add(newCustomerRequest);
          taxi.get().setRequests(updatedCustomerRequests);
          taxiRepository.save(taxi.get());
        }
      });
    }
  }

  private void removeCustomerRequestFromTaxi(TaxiForDatabase taxi, String phoneNumber) {
    List<CustomerRequest> updatedCustomerRequests = new ArrayList<>();
    taxi.getRequests().forEach(request -> {
      if(!request.getPhoneNumber().equals(phoneNumber)) {
        updatedCustomerRequests.add(request);
      }
    });
    taxi.setRequests(updatedCustomerRequests);
    taxiRepository.save(taxi);
  }
}
