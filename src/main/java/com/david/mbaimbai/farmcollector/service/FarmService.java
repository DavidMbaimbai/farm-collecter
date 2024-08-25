package com.david.mbaimbai.farmcollector.service;

import com.david.mbaimbai.farmcollector.dto.FarmDto;
import com.david.mbaimbai.farmcollector.entity.Farm;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.exceptions.ItemAlreadyExistException;
import com.david.mbaimbai.farmcollector.exceptions.ResourceNotFoundException;
import com.david.mbaimbai.farmcollector.mapper.FarmMapper;
import com.david.mbaimbai.farmcollector.mapper.FarmerMapper;
import com.david.mbaimbai.farmcollector.repository.FarmRepository;
import com.david.mbaimbai.farmcollector.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;

    public void save(final FarmDto farmDto){
        var farm = farmMapper.mapFarmDto(farmDto);
        var farmer = farmerRepository.findByNationalId(farmDto.getOwner().getNationalId())
                .orElse(farmerMapper.mapFarmerDto(farmDto.getOwner()));
        farm.setOwner(farmer);
        if (farmRepository.findByName(farmDto.getName()).isPresent()) {
            throw new ItemAlreadyExistException(farmDto.getClass().getSimpleName(), farmDto.getAddress());
        }
        farmMapper.mapFarm(farmRepository.save(farm));
    }

    public FarmDto findByAddress(final String name) {
        var farm = farmRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farm", name)));
        return farmMapper.mapFarm(farm);
    }

    public List<FarmDto> allFarms() {
        return farmMapper.mapFarmList(farmRepository.findAll());
    }

    public void delete(final String name) {
        var crop = farmRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farm", name)));
        farmRepository.delete(crop);
    }


    public void update(final FarmDto farmDto) {
        Farm savedFarm = farmRepository.findByName(farmDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farm",
                        farmDto.getName())));
        Farm mappedFarm = farmMapper.mapFarm(savedFarm, farmDto);
        farmRepository.save(mappedFarm);
    }
}
