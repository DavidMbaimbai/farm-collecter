package com.david.mbaimbai.farmcollector.controller;

import com.david.mbaimbai.farmcollector.dto.ErrorResponseDto;
import com.david.mbaimbai.farmcollector.dto.FarmActivityTrackerDto;
import com.david.mbaimbai.farmcollector.dto.ResponseDto;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.service.FarmActivityTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activity")
public class FarmActivityTrackerController {

    private final FarmActivityTrackerService farmActivityTrackerService;

    @PostMapping("/save")
    @Operation(summary = "Saving farm activities", description = "Saving farm aci")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crop details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Crop details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> save(@RequestBody @Valid FarmActivityTrackerDto farmActivityTrackerDto){
        farmActivityTrackerService.save(farmActivityTrackerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(Constants.STATUS_201)
                        .statusMsg(Constants.STATUS_201_MESSAGE.formatted("Farm Activity Tracker", farmActivityTrackerDto.getActivityType()))
                        .build());
    }

    @GetMapping("/season/{seasonName}/farm/{farmName}")
    @Operation(summary = "Fetching farm activities details ", description = "Getting farm activities details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "farm activities details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "farm activities details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<FarmActivityTrackerDto>> findBySeasonAndFarm(@PathVariable final String seasonName,
                                                                           @PathVariable final String farmName) {
        List<FarmActivityTrackerDto> farmActivityTrackerDtos =
                farmActivityTrackerService.findBySeasonNameAndFarmName(seasonName, farmName);
        return ResponseEntity.ok(farmActivityTrackerDtos);
    }

    @GetMapping("/crop/{cropName}")
    @Operation(summary = "Fetching farm activities details by crop name", description = "Getting farm activities details using the crop name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "farm activities details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "farm activities details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<FarmActivityTrackerDto>> findByCropName(@PathVariable final String cropName) {
        List<FarmActivityTrackerDto> farmActivityTrackerDtos =
                farmActivityTrackerService.findByCropName(cropName);
        return ResponseEntity.ok(farmActivityTrackerDtos);
    }
}
