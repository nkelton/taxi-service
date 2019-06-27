package com.veorider.taxiservice.domain.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiNearby {
    private String plateNumber;
    private float lat;
    private float lng;
}
