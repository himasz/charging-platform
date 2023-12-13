package com.dcs.service;

import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChargeDetailServiceImpl implements ChargeDetailService {
    private final BookingRepository repository;

    @Override
    public void createChargeDetailRecord(ChargeDetailDTO chargeDetailDTO) {
    }

    @Override
    public ChargeDetailDTO getChargeDetailRecord(String chargeId) {
        return null;
    }

    @Override
    public Set<String> searchVehicleChargeDetails(String vin) {
        return null;
    }

}
