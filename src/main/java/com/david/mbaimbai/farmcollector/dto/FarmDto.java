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
public class FarmDto {
    @NotBlank(message = "Farm address required")
    private String address;
    private Double totalHectares;
    private String name;
    private FarmerDto owner;
}
