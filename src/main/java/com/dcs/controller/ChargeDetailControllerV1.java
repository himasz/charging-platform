package com.dcs.controller;

import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.error.ApiError;
import com.dcs.service.ChargeDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.dcs.common.constants.Constants.*;

@RestController
@RequestMapping("/charge/detail/v1")
@RequiredArgsConstructor
public class ChargeDetailControllerV1 {
    private final ChargeDetailService chargeDetailService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Create a charge detail record")
    @ApiResponse(responseCode = "204", description = "Creates a new charge detail record")
    @ApiResponse(responseCode = "400", description = "Some parameters are missing or invalid",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            ))
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public void createChargeDetailRecord(@Valid @RequestBody ChargeDetailDTO chargeDetailDTO) {
        chargeDetailService.createChargeDetailRecord(chargeDetailDTO);
    }

    @GetMapping("/{charge_id}")
    @Operation(summary = "Get a charge detail record by ID")
    @ApiResponse(responseCode = "200", description = "Returns the specified charge detail record as JSON",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ChargeDetailDTO.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there is no charge detail record with given charge_id",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            ))
    public ResponseEntity<ChargeDetailDTO> getChargeDetailRecord(@PathVariable(value = "charge_id") @Min(1) long chargeId) {
        return ResponseEntity.ok(chargeDetailService.getChargeDetailRecord(chargeId));
    }

    @GetMapping("/search/{vin}")
    @Operation(summary = "Get list of charge detail records for a certain vin")
    @ApiResponse(responseCode = "200", description = "Returns list of charge detail records for a certain vin",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there are no charge detail records with given vin",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            ))
    public ResponseEntity<List<ChargeDetailDTO>> searchVehicleChargeDetails(
            @PathVariable @Min(MIN) String vin,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) @Min(MIN) final Integer page,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN) @Max(MAX) final Integer pageSize) {
        return ResponseEntity.ok(chargeDetailService.searchVehicleChargeDetails(vin, page, pageSize));
    }
}