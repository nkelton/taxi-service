package com.veorider.taxiservice.domain.customer;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomerForDatabase {

  @Id
  @GeneratedValue
  private long id;

}
