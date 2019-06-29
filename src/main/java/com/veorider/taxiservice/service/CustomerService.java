package com.veorider.taxiservice.service;

import com.veorider.taxiservice.domain.customer.CustomerForDatabase;
import com.veorider.taxiservice.domain.customer.ResponseStatus;
import com.veorider.taxiservice.domain.customer.TaxiResponse;
import com.veorider.taxiservice.domain.customer.messaging.CustomerRequestPayload;
import com.veorider.taxiservice.domain.customer.messaging.MessageType;
import com.veorider.taxiservice.domain.customer.request.TaxiDecision;
import com.veorider.taxiservice.domain.customer.request.TaxiRequest;
import com.veorider.taxiservice.messaging.CustomerMessageSender;
import com.veorider.taxiservice.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {
  private CustomerRepository customerRepository;
  private CustomerMessageSender customerMessageSender;

  public CustomerService(CustomerRepository customerRepository, CustomerMessageSender customerMessageSender) {
    this.customerRepository = customerRepository;
    this.customerMessageSender = customerMessageSender;
  }

  public List<CustomerForDatabase> retrieveAll() {
    return customerRepository.findAll();
  }

  public void persist(final List<CustomerForDatabase> customers) {
    customers.forEach(customer -> {
      customerRepository.save(customer);
    });
  }

  public boolean decideRequest(String phoneNumber, TaxiDecision taxiDecision) {
    Optional<CustomerForDatabase> customer = customerRepository.findByPhoneNumber(phoneNumber);

    if (customer.isPresent()) {
      //check if customer has already been accepted
      if (customer.get().getAcceptedStatus()) {
        return false;
      }
      else {
        List<TaxiResponse> taxiResponses = customer.get().getResponses();
        List<TaxiResponse> updatedTaxiRespones = new ArrayList<>();

        CustomerRequestPayload payload = CustomerRequestPayload.builder()
            .phoneNumber(phoneNumber)
            .respondedPlate(taxiDecision.getPlateNumber())
            .build();

        //send message to update taxiDb
        if (taxiDecision.getAccept()) {
          customer.get().setAcceptedStatus(true);
          customerMessageSender.sendCustomerRequest(payload.withMessageType(MessageType.ACCEPTED));
        }
        else {
          customerMessageSender.sendCustomerRequest(payload.withMessageType(MessageType.DENIED));
        }

        //update customer db
        taxiResponses.forEach(response -> {
          if (response.getPlateNumber().equals(taxiDecision.getPlateNumber())) {
            updatedTaxiRespones.add(
                TaxiResponse.builder()
                    .plateNumber(response.getPlateNumber())
                    .response(taxiDecision.getAccept())
                    .responseStatus(ResponseStatus.RESPONSE)
                    .build());
          } else {
            updatedTaxiRespones.add(response);
          }
        });
        customer.get().setResponses(updatedTaxiRespones);
        customerRepository.save(customer.get());

        return true;
      }
    }

    return false;
  }

  public String requestTaxi(String phoneNumber, TaxiRequest taxiRequest)
      throws InterruptedException {
    Optional<CustomerForDatabase> customer = customerRepository.findByPhoneNumber(phoneNumber);

    if(customer.isPresent()) {
      List<TaxiResponse> taxiResponses = new ArrayList<>();

      //store list of taxi requests in db
      taxiRequest.getTaxis().forEach(request -> {
        taxiResponses.add(
            TaxiResponse.builder()
              .plateNumber(request)
              .response(false)
              .responseStatus(ResponseStatus.NO_RESPONSE)
              .build()
        );
      });

      customer.get().setResponses(taxiResponses);
      customerRepository.save(customer.get());

      //put request on queue
      customerMessageSender.sendCustomerRequest(
          CustomerRequestPayload.builder()
            .phoneNumber(phoneNumber)
            .lat(customer.get().getLat())
            .lng(customer.get().getLng())
            .licensePlates(taxiRequest.getTaxis())
            .messageType(MessageType.REQUEST)
          .build()
      );
    }
    return pollForTaxiResponse(phoneNumber);
  }

  private String pollForTaxiResponse(String phoneNumber) throws InterruptedException {
    while (true) {
      log.info("Polling for taxi response: {}", phoneNumber);
      Optional<CustomerForDatabase>  customer = customerRepository.findByPhoneNumber(phoneNumber);
      if (customer.isPresent()) {
        //check if customer has been accepted and find corresponding taxi plate
        if (customer.get().getAcceptedStatus()) {
          List<TaxiResponse> acceptedPlate = customer.get().getResponses().stream()
              .filter(response ->
                  response.getResponse() && response.getResponseStatus().equals(ResponseStatus.RESPONSE))
              .collect(Collectors.toList());

          //clear customer responses
//          customer.get().setResponses(Collections.emptyList());
          customerRepository.save(customer.get());

          return acceptedPlate.get(0).getPlateNumber();
        }

        //check if all Taxis have responded
        else {
          List<TaxiResponse> responses = customer.get().getResponses().stream()
              .filter(response ->
                  !response.getResponse() && response.getResponseStatus()
                      .equals(ResponseStatus.RESPONSE))
              .collect(Collectors.toList());

          // all taxis have responded
          if (responses.size() == customer.get().getResponses().size()) {
            return null;
          }
        }
        Thread.sleep(5000);
      }
    }
  }
}
