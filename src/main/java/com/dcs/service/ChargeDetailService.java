package com.dcs.service;

import com.dcs.common.dto.ChargeDetailDTO;

import java.util.List;

public interface ChargeDetailService {

    void createChargeDetailRecord(ChargeDetailDTO chargeDetailDTO);

    ChargeDetailDTO getChargeDetailRecord(long chargeId);

    List<ChargeDetailDTO> searchVehicleChargeDetails(final String vin, final Integer page, final Integer pageSize);
}
