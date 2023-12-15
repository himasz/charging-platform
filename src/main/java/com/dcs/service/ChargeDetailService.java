package com.dcs.service;

import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;

import java.util.List;

public interface ChargeDetailService {

    void createChargeDetailRecord(ChargeDetailDTO chargeDetailDTO);

    ChargeDetailDTO getChargeDetailRecord(long chargeId);

    List<ChargeDetailEntity> searchVehicleChargeDetails(final String vin, final Integer page, final Integer pageSize);
}
