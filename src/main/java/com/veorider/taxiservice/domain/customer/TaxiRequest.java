package com.veorider.taxiservice.domain.customer;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiRequest {
  private List<String> taxis;
}
