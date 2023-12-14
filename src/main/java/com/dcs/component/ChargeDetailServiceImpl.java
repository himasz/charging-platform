package com.dcs.component;

import com.dcs.common.constants.ApiConstants;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;
import com.dcs.common.error.exceptions.DataValidationException;
import com.dcs.mapper.ChargeDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ChargeDetailServiceImpl implements ChargeDetailService {
    private final ChargeDetailRepository repository;
    private final ChargeDetailMapper chargeDetailMapper;
    private static AtomicLong atomicOldEndTime = new AtomicLong();

    @Override
    public void createChargeDetailRecord(final ChargeDetailDTO chargeDetailDTO) {
        checkTimeConstraint(chargeDetailDTO.getEndTime(), chargeDetailDTO.getStartTime());
        repository.save(chargeDetailMapper.INSTANCE.toEntity(chargeDetailDTO));
    }

    @Override
    public ChargeDetailDTO getChargeDetailRecord(final long chargeId) {
        ChargeDetailEntity entity = Optional.ofNullable(repository.getById(chargeId))
                .orElseThrow(() -> new DataValidationException("No prices found for currency: " + chargeId));
        return chargeDetailMapper.INSTANCE.toDTO(entity);
    }

    @Override
    public List<ChargeDetailEntity> searchVehicleChargeDetails(final String vin, final Integer offset, final Integer limit) {
        return Optional.ofNullable(repository.getAllChargeDetailByVin(vin, getPageRequest(offset, limit)))
                .orElseThrow(() -> new DataValidationException("vin: " + vin));
    }

    private static PageRequest getPageRequest(Integer offset, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.ASC, ApiConstants.START_TIME)
                .and(Sort.by(Sort.Direction.ASC, ApiConstants.END_TIME));
        return PageRequest.of(offset, limit, sort);
    }

    private static void checkTimeConstraint(final long endTime, final long startTime) {
        boolean isAfterLastCharge = atomicOldEndTime.get() != 0 && atomicOldEndTime.get() > startTime;
        if (startTime >= endTime && isAfterLastCharge) {
            throw new DataValidationException(String.format("startTime: %s and endTime: %s", startTime, endTime));
        }
        atomicOldEndTime.set(endTime);
    }

}
