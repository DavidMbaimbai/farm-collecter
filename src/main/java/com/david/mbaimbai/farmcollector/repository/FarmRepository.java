package com.david.mbaimbai.farmcollector.repository;

import com.david.mbaimbai.farmcollector.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm, Long> {


    Optional<Farm>  findByName(final String name);
}
