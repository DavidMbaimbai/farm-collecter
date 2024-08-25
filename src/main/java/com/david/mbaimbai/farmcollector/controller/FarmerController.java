package com.david.mbaimbai.farmcollector.controller;

import com.david.mbaimbai.farmcollector.dto.ErrorResponseDto;
import com.david.mbaimbai.farmcollector.dto.FarmerDto;
import com.david.mbaimbai.farmcollector.dto.ResponseDto;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.service.FarmerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/farmer")
@Slf4j
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping("/save")
    @Operation(summary = "Saving farmer", description = "Saving Farmer details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farmer details saved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farmer details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> save(@RequestBody @Valid FarmerDto farmerDto){
        farmerService.save(farmerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(Constants.STATUS_201)
                        .statusMsg(Constants.STATUS_201_MESSAGE.formatted("Farmer", farmerDto.getNationalId()))
                        .build());
    }
    @GetMapping("/fetch-by-name")
    @Operation(summary = "Fetching farmer details by farmer name", description = "Getting Farmer details using the farmer name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farmer details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farmer details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<FarmerDto> retrieveFarmerByName(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Farmer name must be alpha numeric")
                                                      @RequestParam final String fullName) {
        log.debug("allFarmer method start");
        final var farmerDto = farmerService.findByName(fullName);
        log.debug("allFarmer method end");
        return ResponseEntity.ok(farmerDto);
    }

    @GetMapping("/all")
    @Operation(summary = "Fetching all Farmer details", description = "Getting all Farmer details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farmer details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farmer details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<FarmerDto>> allFarmers() {
        log.debug("retrieveFarmerDetails method start");
        final var farmerDto = farmerService.allFarmers();
        log.debug("retrieveFarmerDetails method end");
        return ResponseEntity.ok(farmerDto);
    }

    @PutMapping("/update")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farmer details updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farmer details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateFarmerDetails(@Valid @RequestBody FarmerDto farmerDto) {
        farmerService.updateFarmer(farmerDto);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(Constants.STATUS_200)
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(farmerDto.getFullName()))
                        .build());
    }

    @DeleteMapping("/delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farmer details deleted successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farmer details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteFarmer(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Farmer name must be alpha numeric")
                                                  @RequestParam final String farmerNationalIdentity) {
        farmerService.deleteFarmer(farmerNationalIdentity);
        return ResponseEntity
                .ok(ResponseDto.builder()
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(farmerNationalIdentity))
                        .status(Constants.STATUS_200)
                        .build());
    }
}
