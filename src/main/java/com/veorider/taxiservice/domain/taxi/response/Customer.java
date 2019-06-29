package com.veorider.taxiservice.domain.taxi.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Customer {
  private String phone;
  private float lat;
  private float lng;
}
