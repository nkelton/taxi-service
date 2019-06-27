package com.veorider.taxiservice.domain.taxi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiNearby {
    private String plateNumber;
    private float lat;
    private float lng;
}
