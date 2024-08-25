package com.david.mbaimbai.farmcollector.repository;

import com.david.mbaimbai.farmcollector.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    Optional<Farmer> findByNationalId(final String nationalId);
    Optional<Farmer> findByFullName(final String fullname);
}
