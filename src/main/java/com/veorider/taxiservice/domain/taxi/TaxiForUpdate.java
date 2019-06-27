package com.veorider.taxiservice.domain.taxi;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiForUpdate {
  @JsonProperty(required = true)
  private Boolean available;
  private Float lat;
  private Float lng;
}
