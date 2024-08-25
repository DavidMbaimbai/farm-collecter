package com.david.mbaimbai.farmcollector.service;

import com.david.mbaimbai.farmcollector.dto.CropDto;
import com.david.mbaimbai.farmcollector.entity.Crop;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.exceptions.ItemAlreadyExistException;
import com.david.mbaimbai.farmcollector.exceptions.ResourceNotFoundException;
import com.david.mbaimbai.farmcollector.mapper.CropMapper;
import com.david.mbaimbai.farmcollector.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {
    private final CropRepository cropRepository;
    private final CropMapper cropMapper;

    public void saveCrop(final CropDto cropDto){
        var crop = cropMapper.mapCropDto(cropDto);
        if (cropRepository.findByCropName(cropDto.getCropName()).isPresent()) {
            throw new ItemAlreadyExistException(crop.getClass().getSimpleName(), cropDto.getCropName());
        }
        cropMapper.mapCrop(cropRepository.save(crop));
    }

    public CropDto findByName(final String cropName) {
        var crop = cropRepository.findByCropName(cropName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Crop", cropName)));
        return cropMapper.mapCrop(crop);
    }

    public List<CropDto> allCrops() {
        return cropMapper.mapCropList(cropRepository.findAll());
    }

    public void deleteCrop(final String cropName) {
        var crop = cropRepository.findByCropName(cropName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Crop", cropName)));
        cropRepository.delete(crop);
    }


    public void updateCrop(final CropDto cropDto) {
        Crop savedCrop = cropRepository.findByCropName(cropDto.getCropName())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Crop", cropDto.getCropName())));
        savedCrop.setCropName(cropDto.getCropName());
        cropRepository.save(savedCrop);
    }

}
