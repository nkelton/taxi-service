package com.veorider.taxiservice.domain.taxi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLocation {
  private Float distance;
  private Float lat;
  private Float lng;
}
