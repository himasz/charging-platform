package com.dcs.service;


import com.dcs.TestData;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;
import com.dcs.common.error.exceptions.DataNotFoundException;
import com.dcs.common.error.exceptions.DataValidationException;
import com.dcs.mapper.ChargeDetailMapper;
import com.dcs.repository.ChargeDetailRepository;
import org.junit.jupiter.api.*;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChargeDetailServiceImplTest extends TestData {
    ChargeDetailRepository repository = mock(ChargeDetailRepository.class);
    ChargeDetailService service = new ChargeDetailServiceImpl(repository, ChargeDetailMapper.INSTANCE);
    ;

    @Test
    @Order(1)
    void test_createChargeDetailRecord_successfully() {
        //given
        ChargeDetailDTO detailDTO1 = new ChargeDetailDTO(ID_1, VIN_1, START_1, END_1, PRICE_1);
        //when
        service.createChargeDetailRecord(detailDTO1);
        //then
        verify(repository, times(1)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO1));
    }

    @Test
    @Order(2)
    void test_createChargeDetailRecord_successfulCheckTimeConstraintWhenCreatingAnotherEntity() {
        //given
        long startTime1 = System.currentTimeMillis() + ONE_MINUTES;
        long endTime1 = startTime1 + ONE_MINUTES;
        long startTime2 = endTime1 + ONE_MINUTES;
        long endTime2 = startTime2 + ONE_MINUTES;
        ChargeDetailDTO detailDTO1 = new ChargeDetailDTO(ID_1, VIN_1, startTime1, endTime1, PRICE_1);
        ChargeDetailDTO detailDTO2 = new ChargeDetailDTO(ID_2, VIN_2, startTime2, endTime2, PRICE_2);
        //when
        service.createChargeDetailRecord(detailDTO1);
        service.createChargeDetailRecord(detailDTO2);
        //then
        verify(repository, times(1)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO1));
        verify(repository, times(1)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO2));
    }

    @Test
    @Order(3)
    void test_createChargeDetailRecord_FailedCheckTimeConstraintWithStartTimeSameAsEndTime() {
        //given
        ChargeDetailDTO detailDTO1 = new ChargeDetailDTO(ID_1, VIN_1, START_1, START_1, PRICE_1);
        //when
        service = new ChargeDetailServiceImpl(repository, ChargeDetailMapper.INSTANCE);
        assertThrows(DataValidationException.class, () -> service.createChargeDetailRecord(detailDTO1));
        //then
        verify(repository, times(0)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO1));
    }

    @Test
    @Order(4)
    void test_createChargeDetailRecord_FailedCheckTimeConstraintWhenCreatingAnotherEntityWithSameTimes() {
        //given
        long startTime = System.currentTimeMillis() + TEN_MINUTES;
        long endTime = startTime + ONE_MINUTES;
        ChargeDetailDTO detailDTO1 = new ChargeDetailDTO(ID_1, VIN_1, startTime, endTime, PRICE_1);
        ChargeDetailDTO detailDTO2 = new ChargeDetailDTO(ID_2, VIN_2, startTime, endTime, PRICE_2);
        //when
        service.createChargeDetailRecord(detailDTO1);
        assertThrows(DataValidationException.class, () -> service.createChargeDetailRecord(detailDTO2));
        //then
        verify(repository, times(1)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO1));
        verify(repository, times(0)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO2));
    }

    @Test
    void test_getChargeDetailRecord_successfully() {
        //given
        when(repository.getById(ID_1)).thenReturn(new ChargeDetailEntity(ID_1, VIN_1, START_1, END_1, PRICE_1));
        //when
        ChargeDetailDTO chargeDetailDTO = service.getChargeDetailRecord(ID_1);
        //then
        Assertions.assertEquals(ID_1, chargeDetailDTO.getId());
        Assertions.assertEquals(VIN_1, chargeDetailDTO.getVin());
        Assertions.assertEquals(END_1, chargeDetailDTO.getEndTime());
        Assertions.assertEquals(START_1, chargeDetailDTO.getStartTime());
        Assertions.assertEquals(PRICE_1, chargeDetailDTO.getPrice());
    }


    @Test
    void test_getChargeDetailRecord_throwsDataNotFoundException() {
        when(repository.getById(ID_2)).thenThrow(new EntityNotFoundException());
        assertThrows(DataNotFoundException.class, () -> service.getChargeDetailRecord(ID_2));
    }
//
//    @Test
//    void test_getDepartmentBookingIds_noFoundDepartmentThrowsInvalidParamException() {
//        assertThrows(MissingDataException.class, () -> service.getDepartmentBookingIds(DEPARTMENT_1));
//    }
//
//    @Test
//    void test_getCurrencySum_noFoundCurrencyThrowsInvalidParamException() {
//        assertThrows(MissingDataException.class, () -> service.getCurrencySum(EUR.name()));
//    }

}