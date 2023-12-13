package com.dcs.service;

import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.dto.CurrencySumDTO;

import java.util.Set;

public interface ChargeDetailService {

    void createChargeDetailRecord(ChargeDetailDTO chargeDetailDTO);

    ChargeDetailDTO getChargeDetailRecord(String chargeId);

    Set<String> searchVehicleChargeDetails(String vin);
}
