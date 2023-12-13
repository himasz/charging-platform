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
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/charge/detail/v1")
@RequiredArgsConstructor
public class ChargeDetailControllerV1 implements IChargeDetailControllerV1 {
    private final ChargeDetailService chargeDetailService;

    @PutMapping("/create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Create a booking")
    @ApiResponse(responseCode = "204", description = "Creates a new booking and sends an e-mail with the details")
    @ApiResponse(responseCode = "400", description = "Some parameters are missing or invalid",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            ))
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    public void createChargeDetailRecord(@Valid @RequestBody ChargeDetailDTO chargeDetailDTO) {
        // TODO document why this method is empty
    }

    @GetMapping("/{charge_id}")
    @Operation(summary = "Get a booking by ID")
    @ApiResponse(responseCode = "200", description = "Returns the specified booking as JSON",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ChargeDetailDTO.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there is no booking with given bookingId",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            ))
    public ResponseEntity<ChargeDetailDTO> getChargeDetailRecord(@PathVariable(value = "charge_id") @Min(1) String chargeId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{vin}")
    @Operation(summary = "Get booking IDs for a department")
    @ApiResponse(responseCode = "200", description = "Returns booking IDs for a department",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class)
            ))
    @ApiResponse(responseCode = "400", description = "when there is no booking with given department",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)
            ))
    public ResponseEntity<Set<String>> searchVehicleChargeDetails(@PathVariable @Min(1) String vin) {
        return ResponseEntity.ok().build();
    }
}