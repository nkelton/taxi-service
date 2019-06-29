package com.veorider.taxiservice.domain.taxi.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaxiNearby {
    private String plateNumber;
    private Float lat;
    private Float lng;
}
