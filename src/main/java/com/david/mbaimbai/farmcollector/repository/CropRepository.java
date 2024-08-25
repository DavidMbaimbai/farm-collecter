package com.david.mbaimbai.farmcollector.repository;

import com.david.mbaimbai.farmcollector.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    Optional<Crop> findByCropName(String cropName);
}
