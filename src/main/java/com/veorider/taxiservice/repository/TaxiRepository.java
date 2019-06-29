package com.veorider.taxiservice.repository;

import com.veorider.taxiservice.domain.taxi.TaxiForDatabase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiRepository extends JpaRepository<TaxiForDatabase, Long> {

  Optional<TaxiForDatabase> findByPlateNumber(final String plateNumber);
  List<TaxiForDatabase> findByAvailableTrue();
}
