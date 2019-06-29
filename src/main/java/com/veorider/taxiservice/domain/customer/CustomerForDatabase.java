package com.veorider.taxiservice.domain.customer;


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
public class CustomerForDatabase {
  @Id
  @GeneratedValue
  private long id;
  @NotNull
  @Column(name = "phone_number", unique = true)
  private String phoneNumber;
  @NotNull
  private Float lat;
  @NotNull
  private Float lng;
  @NotNull
  private Boolean acceptedStatus;
  @ElementCollection
  @Column(name = "responses")
  private List<TaxiResponse> responses;

}
