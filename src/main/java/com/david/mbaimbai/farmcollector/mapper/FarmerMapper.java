package com.david.mbaimbai.farmcollector.mapper;

import com.david.mbaimbai.farmcollector.dto.FarmerDto;
import com.david.mbaimbai.farmcollector.entity.Farmer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FarmerMapper {

    Farmer mapFarmerDto(final FarmerDto farmerDto);
    FarmerDto mapFarmer(final Farmer farmer);

    Farmer farmer(@MappingTarget Farmer farmer, FarmerDto farmerDto);

    List<FarmerDto> mapFarmerList(final List<Farmer> farmers);
}
