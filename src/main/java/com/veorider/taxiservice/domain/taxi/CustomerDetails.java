package com.veorider.taxiservice.domain.taxi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetails {
  private String phone;
  private float lat;
  private float lng;

}
