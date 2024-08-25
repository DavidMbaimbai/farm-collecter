package com.david.mbaimbai.farmcollector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SeasonDto {
    @NotBlank(message = "Season name required")
    private String seasonName;
    private String period;
}
