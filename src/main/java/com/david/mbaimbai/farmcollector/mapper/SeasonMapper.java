package com.david.mbaimbai.farmcollector.mapper;

import com.david.mbaimbai.farmcollector.dto.SeasonDto;
import com.david.mbaimbai.farmcollector.entity.Season;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeasonMapper {

    Season mapSeasonDto(final SeasonDto seasonDto);

    SeasonDto mapSeason(final Season season);

    List<SeasonDto> mapSeasonList(final List<Season> seasons);

    Season mapSeason(@MappingTarget Season season, SeasonDto seasonDto);
}
