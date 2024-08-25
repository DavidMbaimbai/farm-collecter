package com.david.mbaimbai.farmcollector.service;

import com.david.mbaimbai.farmcollector.dto.FarmActivityTrackerDto;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.exceptions.ResourceNotFoundException;
import com.david.mbaimbai.farmcollector.mapper.CropMapper;
import com.david.mbaimbai.farmcollector.mapper.FarmActivityTrackerMapper;
import com.david.mbaimbai.farmcollector.mapper.FarmMapper;
import com.david.mbaimbai.farmcollector.mapper.SeasonMapper;
import com.david.mbaimbai.farmcollector.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmActivityTrackerService {

    private final FarmActivityTrackerRepository farmActivityTrackerRepository;
    private final FarmActivityTrackerMapper farmActivityTrackerMapper;
    private final CropMapper cropMapper;
    private final FarmMapper farmMapper;
    private final SeasonMapper seasonMapper;
    private final FarmerRepository farmerRepository;
    private final CropRepository cropRepository;
    private final FarmRepository farmRepository;
    private final SeasonRepository seasonRepository;

    public void save(final FarmActivityTrackerDto farmActivityTrackerDto) {
        var farmActivityTracker = farmActivityTrackerMapper.mapFarmActivityTrackerDto(farmActivityTrackerDto);
        var crop = cropRepository.findByCropName(farmActivityTrackerDto.getCropDto().getCropName())
                .orElse(cropMapper.mapCropDto(farmActivityTrackerDto.getCropDto()));
        farmActivityTracker.setCrop(crop);
        var farm = farmRepository.findByName(farmActivityTrackerDto.getFarmDto().getName())
                        .orElse(farmMapper.mapFarmDto(farmActivityTrackerDto.getFarmDto()));
        farmActivityTracker.setFarm(farm);
        var season = seasonRepository.findBySeasonName(farmActivityTrackerDto.getSeasonDto().getSeasonName())
                .orElse(seasonMapper.mapSeasonDto(farmActivityTrackerDto.getSeasonDto()));
        farmActivityTracker.setSeason(season);

        farmActivityTrackerMapper.mapFarmActivityTracker(farmActivityTrackerRepository.save(farmActivityTracker));
    }

    public List<FarmActivityTrackerDto> findByCropName(final String cropName) {
        var crop = cropRepository.findByCropName(cropName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farmer", cropName)));
        return crop.getFarmActivityTracker()
                .stream()
                .map(farmActivityTracker -> {
                    var item = farmActivityTrackerMapper.mapFarmActivityTracker(farmActivityTracker);
                    item.setFarmDto(farmMapper.mapFarm(farmActivityTracker.getFarm()));
                    item.setSeasonDto(seasonMapper.mapSeason(farmActivityTracker.getSeason()));
                    item.setCropDto(cropMapper.mapCrop(farmActivityTracker.getCrop()));
                    return item;
                })
                .collect(Collectors.toList());


    }

    public List<FarmActivityTrackerDto> allFarmActivityTracker() {
        return farmActivityTrackerMapper.mapFarmActivityTrackerList(farmActivityTrackerRepository.findAll());
    }

    public void deleteFarmer(final String farmerNationalIdentity) {
        var crop = farmerRepository.findByNationalId(farmerNationalIdentity)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farmer", farmerNationalIdentity)));
        farmerRepository.delete(crop);
    }

    public List<FarmActivityTrackerDto> findBySeasonNameAndFarmName(final String seasonName, final String farmName) {
        seasonRepository.findBySeasonName(seasonName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Season", seasonName)));
        var farm = farmRepository.findByName(farmName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farm", farmName)));

        return farm.getFarmActivityTracker()
                .stream()
                .filter(farmActivityTracker -> farmActivityTracker.getSeason().getSeasonName().equalsIgnoreCase(seasonName))
                .map(farmActivityTracker -> {
                    var item = farmActivityTrackerMapper.mapFarmActivityTracker(farmActivityTracker);
                    item.setFarmDto(farmMapper.mapFarm(farmActivityTracker.getFarm()));
                    item.setSeasonDto(seasonMapper.mapSeason(farmActivityTracker.getSeason()));
                    item.setCropDto(cropMapper.mapCrop(farmActivityTracker.getCrop()));
                    return item;
                })
                .collect(Collectors.toList());
    }
}
