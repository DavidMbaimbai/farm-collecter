package com.david.mbaimbai.farmcollector.mapper;

import com.david.mbaimbai.farmcollector.dto.FarmDto;
import com.david.mbaimbai.farmcollector.entity.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FarmMapper {

    Farm mapFarmDto(final FarmDto farmDto);
    FarmDto mapFarm(final Farm farm);

    Farm mapFarm(@MappingTarget Farm farm, FarmDto farmDto);

    List<FarmDto> mapFarmList(final List<Farm> farms);

    Farm farm(@MappingTarget Farm farmer, FarmDto farmDto);
}
