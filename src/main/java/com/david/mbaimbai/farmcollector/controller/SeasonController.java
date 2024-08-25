package com.david.mbaimbai.farmcollector.controller;

import com.david.mbaimbai.farmcollector.dto.ErrorResponseDto;
import com.david.mbaimbai.farmcollector.dto.ResponseDto;
import com.david.mbaimbai.farmcollector.dto.SeasonDto;
import com.david.mbaimbai.farmcollector.enums.Constants;
import com.david.mbaimbai.farmcollector.service.SeasonService;
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
@RequestMapping("/season")
@Slf4j
public class SeasonController {

    private final SeasonService seasonService;

    @PostMapping("/save")
    @Operation(summary = "Saving seasons", description = "Saving Season details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season details saved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Season details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> save(@RequestBody @Valid SeasonDto seasonDto){
        seasonService.save(seasonDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .status(Constants.STATUS_201)
                        .statusMsg(Constants.STATUS_201_MESSAGE.formatted("Season", seasonDto.getSeasonName()))
                        .build());
    }
    @GetMapping("/fetch-by-name")
    @Operation(summary = "Fetching season details by season name", description = "Getting Season details using the season name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Season details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<SeasonDto> retrieveSeasonByName(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Season name must be alpha numeric")
                                                      @RequestParam final String seasonName) {
        log.debug("allSeasons method start");
        final var seasonDto = seasonService.findByName(seasonName);
        log.debug("allSeasons method end");
        return ResponseEntity.ok(seasonDto);
    }

    @GetMapping("/all")
    @Operation(summary = "Fetching all Season details", description = "Getting all Season details ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season details retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Season details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<SeasonDto>> allSeasons() {
        log.debug("retrieveSeasonsDetails method start");
        final var seasonDto =seasonService.allSeasons();
        log.debug("retrieveSeasonsDetails method end");
        return ResponseEntity.ok(seasonDto);
    }

    @PutMapping("/update")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season details updated successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Season details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateSeasonDetails(@Valid @RequestBody SeasonDto seasonDto) {
        seasonService.update(seasonDto);
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(Constants.STATUS_200)
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(seasonDto.getSeasonName()))
                        .build());
    }

    @DeleteMapping("/delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Season details deleted successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Season details not found!",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Unknown exception occurred during processing",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteCroSeasonp(@Pattern(regexp = "^$|[a-zA-Z0-9]{10}",
            message = "Season name must be alpha numeric")
                                                  @RequestParam final String seasonName) {
       seasonService.delete(seasonName);
        return ResponseEntity
                .ok(ResponseDto.builder()
                        .statusMsg(Constants.STATUS_200_MESSAGE.formatted(seasonName))
                        .status(Constants.STATUS_200)
                        .build());
    }
}
