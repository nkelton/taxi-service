package com.veorider.taxiservice.domain.customer.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiDecision {
  private Boolean accept;
  private String plateNumber;
}
