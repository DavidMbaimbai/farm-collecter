package com.david.mbaimbai.farmcollector.dto;

import com.david.mbaimbai.farmcollector.enums.ActivityType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FarmActivityTrackerDto {
    @NotBlank(message = "Planting area required")
    private Double plantingArea;
    private CropDto cropDto;
    @NotBlank(message = "Product  required")
    private Double product;
    @NotBlank(message = "Activity type Id required")
    private ActivityType activityType;
    private FarmDto farmDto;
    private SeasonDto seasonDto;
}
