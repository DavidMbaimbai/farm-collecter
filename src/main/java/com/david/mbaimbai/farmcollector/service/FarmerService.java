package com.david.mbaimbai.farmcollector.service;

import com.david.mbaimbai.farmcollector.dto.FarmerDto;
import com.david.mbaimbai.farmcollector.entity.Farmer;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.exceptions.ItemAlreadyExistException;
import com.david.mbaimbai.farmcollector.exceptions.ResourceNotFoundException;
import com.david.mbaimbai.farmcollector.mapper.FarmerMapper;
import com.david.mbaimbai.farmcollector.repository.FarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {
    
    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;

    public void save(final FarmerDto farmerDto){
        var farmer = farmerMapper.mapFarmerDto(farmerDto);
        if (farmerRepository.findByNationalId(farmerDto.getNationalId()).isPresent()) {
            throw new ItemAlreadyExistException(farmer.getClass().getSimpleName(), farmerDto.getNationalId());
        }
        farmerMapper.mapFarmer(farmerRepository.save(farmer));
    }

    public FarmerDto findByNationalID(final String farmerNationalIdentity) {
        var farmer = farmerRepository.findByNationalId(farmerNationalIdentity)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farmer", farmerNationalIdentity)));
        return farmerMapper.mapFarmer(farmer);
    }

    public FarmerDto findByName(final String fullName) {
        var farmer = farmerRepository.findByNationalId(fullName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farmer", fullName)));
        return farmerMapper.mapFarmer(farmer);
    }

    public List<FarmerDto> allFarmers() {
        return farmerMapper.mapFarmerList(farmerRepository.findAll());
    }

    public void deleteFarmer(final String farmerNationalIdentity) {
        var crop = farmerRepository.findByNationalId(farmerNationalIdentity)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farmer", farmerNationalIdentity)));
        farmerRepository.delete(crop);
    }


    public void updateFarmer(final FarmerDto farmerDto) {
        Farmer farmer = farmerRepository.findByNationalId(farmerDto.getNationalId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Farmer", farmerDto.getNationalId())));
        Farmer mappedFarmer = farmerMapper.farmer(farmer, farmerDto);
        farmerRepository.save(mappedFarmer);
    }
}
