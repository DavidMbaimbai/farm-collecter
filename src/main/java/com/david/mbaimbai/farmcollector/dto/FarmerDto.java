package com.david.mbaimbai.farmcollector.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FarmerDto {
    @NotBlank(message = "National Id required")
    private String nationalId;
    @NotBlank(message = "Fullname is required")
    private String fullName;
}
