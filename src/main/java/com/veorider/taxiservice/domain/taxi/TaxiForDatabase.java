package com.veorider.taxiservice.domain.taxi;

import com.veorider.taxiservice.domain.customer.CustomerRequest;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaxiForDatabase {
  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  @Column(name="plate_number", unique = true)
  private String plateNumber;
  @NotNull
  private Boolean available = true;
  @NotNull
  private Float lat;
  @NotNull
  private Float lng;
  @Column(name = "requests")
  @ElementCollection
  private List<CustomerRequest> requests;

  public TaxiForDatabase() {}

}
