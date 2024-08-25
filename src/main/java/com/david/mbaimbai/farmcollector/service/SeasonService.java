package com.david.mbaimbai.farmcollector.service;

import com.david.mbaimbai.farmcollector.dto.SeasonDto;
import com.david.mbaimbai.farmcollector.entity.Season;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.exceptions.ItemAlreadyExistException;
import com.david.mbaimbai.farmcollector.exceptions.ResourceNotFoundException;
import com.david.mbaimbai.farmcollector.mapper.SeasonMapper;
import com.david.mbaimbai.farmcollector.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;

    public void save(final SeasonDto seasonDto){
        var season = seasonMapper.mapSeasonDto(seasonDto);
        if (seasonRepository.findBySeasonName(seasonDto.getSeasonName()).isPresent()) {
            throw new ItemAlreadyExistException(season.getClass().getSimpleName(), seasonDto.getSeasonName());
        }
        seasonMapper.mapSeason(seasonRepository.save(season));
    }

    public SeasonDto findByName(final String seasonName) {
        var season = seasonRepository.findBySeasonName(seasonName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Season", seasonName)));
        return seasonMapper.mapSeason(season);
    }

    public List<SeasonDto> allSeasons() {
        return seasonMapper.mapSeasonList(seasonRepository.findAll());
    }

    public void delete(final String seasonName) {
        var crop = seasonRepository.findBySeasonName(seasonName)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Season", seasonName)));
        seasonRepository.delete(crop);
    }


    public void update(final SeasonDto seasonDto) {
        Season savedSeason = seasonRepository.findBySeasonName(seasonDto.getSeasonName())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.STATUS_400_MESSAGE.formatted("Season", seasonDto.getSeasonName())));
        var season = seasonMapper.mapSeason(savedSeason, seasonDto);
        seasonRepository.save(season);
    }
}
