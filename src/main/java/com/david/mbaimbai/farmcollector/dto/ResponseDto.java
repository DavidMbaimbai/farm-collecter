package com.david.mbaimbai.farmcollector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Response", description = "Response for successful processing.")
public class ResponseDto {
    @Schema(name = "Status", description = "Success status depending on the operation.")
    private String status;
    @Schema(name = "Status Message", description = "Status message depending on the operation.")
    private String statusMsg;
}
