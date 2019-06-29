package com.veorider.taxiservice.domain.customer.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Wither;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Wither
public class CustomerRequestPayload implements Serializable {
  private String phoneNumber;
  private Float lat;
  private Float lng;
  private List<String> licensePlates;
  private LocalDateTime requestTime;
  private MessageType messageType;
  private String respondedPlate;

  public CustomerRequestPayload() {}
}
