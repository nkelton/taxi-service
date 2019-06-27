package com.veorider.taxiservice.repository;

import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRepository extends JpaRepository<TaxiForDatabase, Long> {

  TaxiForDatabase findByPlateNumber(String plateNumber);

}
