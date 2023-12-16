package com.dcs.service;

import com.dcs.common.constants.Constants;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;
import com.dcs.common.error.exceptions.DataNotFoundException;
import com.dcs.common.error.exceptions.DataValidationException;
import com.dcs.mapper.ChargeDetailMapper;
import com.dcs.repository.ChargeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.dcs.common.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class ChargeDetailServiceImpl implements ChargeDetailService {
    private final ChargeDetailRepository repository;
    private final ChargeDetailMapper mapper;
    private static final AtomicLong atomicOldEndTime = new AtomicLong();

    @Override
    public void createChargeDetailRecord(final ChargeDetailDTO chargeDetailDTO) {
        checkTimeConstraint(chargeDetailDTO.getEndTime(), chargeDetailDTO.getStartTime());
        repository.save(mapper.toEntity(chargeDetailDTO));
    }

    @Override
    public ChargeDetailDTO getChargeDetailRecord(final long chargeId) {
        try {
            return mapper.toDTO(repository.getById(chargeId));
        } catch (EntityNotFoundException e) {
            throw new DataNotFoundException(NO_CHARGE_DETAIL_FOUND_FOR + chargeId);
        }
    }

    @Override
    public List<ChargeDetailDTO> searchVehicleChargeDetails(final String vin, final Integer page, final Integer pageSize) {
        List<ChargeDetailEntity> chargeDetailEntities =
                repository.getAllChargeDetailByVin(vin, getPageRequest(page, pageSize));
        if (chargeDetailEntities.isEmpty()) {
            throw new DataNotFoundException(NO_CHARGE_DETAIL_FOUND_FOR + vin);
        }
        return mapper.toDTOList(chargeDetailEntities);
    }

    private static PageRequest getPageRequest(Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, Constants.START_TIME)
                .and(Sort.by(Sort.Direction.ASC, Constants.END_TIME));
        return PageRequest.of(page, pageSize, sort);
    }

    private static void checkTimeConstraint(final long endTime, final long startTime) {
        boolean isNotAfterLastCharge = atomicOldEndTime.get() != 0 && atomicOldEndTime.get() > startTime;
        if (startTime >= endTime || isNotAfterLastCharge) {
            throw new DataValidationException(String.format(START_TIME_S_END_TIME_S_MESSAGE, startTime, endTime));
        }
        atomicOldEndTime.set(endTime);
    }
}
