package com.veorider.taxiservice.messaging;

import com.veorider.taxiservice.config.RabbitConfig;
import com.veorider.taxiservice.domain.customer.messaging.CustomerRequestPayload;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessageSender {
  private final RabbitTemplate rabbitTemplate;

  public CustomerMessageSender(RabbitTemplate rabbitTemplate, Exchange exchange) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendCustomerRequest(CustomerRequestPayload customerRequestPayload) {
      rabbitTemplate.convertAndSend(RabbitConfig.CUSTOMER_REQUEST_QUEUE, customerRequestPayload);
  }

}
