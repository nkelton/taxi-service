package com.veorider.taxiservice.domain.customer;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TaxiResponse {
  private String plateNumber;
  private Boolean response;
  private ResponseStatus responseStatus;
}
