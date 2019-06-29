package com.veorider.taxiservice.domain.customer;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Embeddable
public class CustomerRequest {
  private String phoneNumber;
  private String licensePlates;
  private LocalDateTime requestTime;
  private Float lat;
  private Float lng;

  public CustomerRequest() {}
}
