package com.veorider.taxiservice.domain.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableTaxis {
  private float distance;
  private float lat;
  private float lng;
}
