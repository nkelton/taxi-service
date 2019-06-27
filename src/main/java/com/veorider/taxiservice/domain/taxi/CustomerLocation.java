package com.veorider.taxiservice.domain.taxi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerLocation {
  private float distance;
  private float lat;
  private float lng;
}
