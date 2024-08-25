package com.david.mbaimbai.farmcollector.repository;

import com.david.mbaimbai.farmcollector.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    Optional<Season> findBySeasonName(String seasonName);
}
