package com.david.mbaimbai.farmcollector.controller;

import com.david.mbaimbai.farmcollector.dto.ErrorResponseDto;
import com.david.mbaimbai.farmcollector.dto.FarmDto;
import com.david.mbaimbai.farmcollector.dto.ResponseDto;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.service.FarmService;
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
@RequestMapping("/farm")
@Slf4j
public class FarmController {

    private  final FarmService farmService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@RequestBody @Valid FarmDto farmDto){
        farmService.save(farmDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(Constants.STATUS_201)
                        .statusMsg(Constants.STATUS_201_MESSAGE.formatted("Farm", farmDto.getAddress()))
                        .build());
    }
    @GetMapping("/fetch-by-name")
    @Operation(summary = "Fetching farm details by fam", description = "Getting Farm details using the farm name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farm details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farm details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<FarmDto> retrieveFarmByName(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Farm name must be alpha numeric")
                                                      @RequestParam final String name) {
        log.debug("allFarm method start");
        final var farmDto = farmService.findByAddress(name);
        log.debug("allFarm method end");
        return ResponseEntity.ok(farmDto);
    }

    @GetMapping("/all")
    @Operation(summary = "Fetching all Farm details", description = "Getting all farm details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farm details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farm details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<FarmDto>> allFarms() {
        log.debug("retrieveFarmDetails method start");
        final var farmDto= farmService.allFarms();
        log.debug("retrieveFarmDetails method end");
        return ResponseEntity.ok(farmDto);
    }

    @PutMapping("/update")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farm details updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farm details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateFarmDetails(@Valid @RequestBody FarmDto farmDto) {
        farmService.update(farmDto);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(Constants.STATUS_200)
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(farmDto.getName()))
                        .build());
    }

    @DeleteMapping("/delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farm details deleted successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Farm details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteFarm(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Farm name must be alpha numeric")
                                                  @RequestParam final String name) {
        farmService.delete(name);
        return ResponseEntity
                .ok(ResponseDto.builder()
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(name))
                        .status(Constants.STATUS_200)
                        .build());
    }
}
