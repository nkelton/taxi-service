package com.veorider.taxiservice.domain.taxi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaxiForDatabase {
  @Id
  @GeneratedValue
  private Long id;
  @Column(name="plate_number", unique = true)
  private String plateNumber;
  private Boolean available;
  private Float lat;
  private Float lng;

  public TaxiForDatabase() {}

}
