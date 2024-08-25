package com.david.mbaimbai.farmcollector.mapper;

import com.david.mbaimbai.farmcollector.dto.FarmActivityTrackerDto;
import com.david.mbaimbai.farmcollector.entity.FarmActivityTracker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FarmActivityTrackerMapper {

    FarmActivityTracker mapFarmActivityTrackerDto(final FarmActivityTrackerDto farmActivityTrackerDto);

    FarmActivityTrackerDto mapFarmActivityTracker(final FarmActivityTracker farmActivityTracker);

    List<FarmActivityTrackerDto> mapFarmActivityTrackerList(final List<FarmActivityTracker> farmActivityTrackers);

    FarmActivityTracker mapFarmActivityTracker(@MappingTarget FarmActivityTracker farmActivityTracker, FarmActivityTrackerDto farmActivityTrackerDto);
}
