package com.david.mbaimbai.farmcollector.controller;

import com.david.mbaimbai.farmcollector.dto.CropDto;
import com.david.mbaimbai.farmcollector.dto.ErrorResponseDto;
import com.david.mbaimbai.farmcollector.dto.ResponseDto;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.service.CropService;
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
@RequestMapping("/crops")
@Slf4j
public class CropController {

    private final CropService cropService;

    @PostMapping("/save")
    @Operation(summary = "Saving crops", description = "Saving Crop details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crop details saved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Crop details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> save(@RequestBody @Valid CropDto cropDto){
         cropService.saveCrop(cropDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(Constants.STATUS_201)
                        .statusMsg(Constants.STATUS_201_MESSAGE.formatted("Crop", cropDto.getCropName()))
                        .build());
    }

    @GetMapping("/fetch-by-name")
    @Operation(summary = "Fetching crop details by crop name", description = "Getting Crop details using the crop name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crop details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Crop details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<CropDto> retrieveCropByName(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Crop name must be alpha numeric")
                                                                @RequestParam final String cropName) {
        log.debug("allCrops method start");
        final var cropDto = cropService.findByName(cropName);
        log.debug("allCrops method end");
        return ResponseEntity.ok(cropDto);
    }

    @GetMapping("/all")
    @Operation(summary = "Fetching all Crop details", description = "Getting all crop details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crop details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Crop details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<CropDto>> allCrops() {
        log.debug("retrieveCropDetails method start");
        final var cropDto = cropService.allCrops();
        log.debug("etrieveCropDetails method end");
        return ResponseEntity.ok(cropDto);
    }

    @PutMapping("/update")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crop details updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Crop details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateCropDetails(@Valid @RequestBody CropDto cropDto) {
        cropService.updateCrop(cropDto);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(Constants.STATUS_200)
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(cropDto.getCropName()))
                        .build());
    }

    @DeleteMapping("/delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crop details deleted successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Crop details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteCrop(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Crop name must be alpha numeric")
                                                  @RequestParam final String cropName) {
        cropService.deleteCrop(cropName);
        return ResponseEntity
                .ok(ResponseDto.builder()
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(cropName))
                        .status(Constants.STATUS_200)
                        .build());
    }
}
