package com.david.mbaimbai.farmcollector.mapper;

import com.david.mbaimbai.farmcollector.dto.CropDto;
import com.david.mbaimbai.farmcollector.entity.Crop;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CropMapper {

    Crop mapCropDto(final CropDto cropDto);
    CropDto mapCrop(final Crop crop);

    List<CropDto> mapCropList(final List<Crop> crops);
}
