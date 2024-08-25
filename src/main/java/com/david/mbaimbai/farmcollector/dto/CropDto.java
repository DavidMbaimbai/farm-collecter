package com.david.mbaimbai.farmcollector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CropDto {
    @NotBlank(message = "Crop name must be provided")
    private String cropName;
}
