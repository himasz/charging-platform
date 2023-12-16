package com.dcs.service;

import com.dcs.TestData;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;
import com.dcs.common.error.exceptions.DataNotFoundException;
import com.dcs.common.error.exceptions.DataValidationException;
import com.dcs.mapper.ChargeDetailMapper;
import com.dcs.repository.ChargeDetailRepository;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChargeDetailServiceImplTest extends TestData {
    private static ChargeDetailRepository repository = mock(ChargeDetailRepository.class);
    private static ChargeDetailServiceImpl service = new ChargeDetailServiceImpl(repository, ChargeDetailMapper.INSTANCE);

    @BeforeAll
    public static void beforeAll() {
        service.resetTimer();
    }

    @Test
    @Order(1)
    void testA_createChargeDetailRecord_successfully() {
        //given
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis() + ONE_MINUTE;
        ChargeDetailDTO detailDTO1 = new ChargeDetailDTO(ID_1, VIN_1, startTime, endTime, PRICE_1);
        //when
        service.createChargeDetailRecord(detailDTO1);
        //then
        verify(repository, times(1)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO1));
    }

    @Test
    @Order(2)
    void testB_createChargeDetailRecord_successfulCheckTimeConstraintWhenCreatingAnotherEntity() {
        //given
        long startTime1 = System.currentTimeMillis() + ONE_MINUTE;
        long endTime1 = startTime1 + ONE_MINUTE;
        long startTime2 = endTime1 + ONE_MINUTE;
        long endTime2 = startTime2 + ONE_MINUTE;
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
    void testC_createChargeDetailRecord_FailedCheckTimeConstraintWithStartTimeSameAsEndTime() {
        //given
        ChargeDetailDTO detailDTO1 = new ChargeDetailDTO(ID_1, VIN_1, START_1, START_1, PRICE_1);
        //when
        assertThrows(DataValidationException.class, () -> service.createChargeDetailRecord(detailDTO1));
        //then
        verify(repository, times(0)).save(ChargeDetailMapper.INSTANCE.toEntity(detailDTO1));
    }

    @Test
    @Order(4)
    void testD_createChargeDetailRecord_FailedCheckTimeConstraintWhenCreatingAnotherEntityWithSameTimes() {
        //given
        long startTime = System.currentTimeMillis() + TEN_MINUTES;
        long endTime = startTime + ONE_MINUTE;
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

    @Test
    void test_searchVehicleChargeDetails_successfully() {
        //given
        ChargeDetailEntity entity = new ChargeDetailEntity(ID_1, VIN_1, START_1, END_1, PRICE_1);
        when(repository.getAllChargeDetailByVin(eq(VIN_1), any(Pageable.class))).thenReturn(List.of(entity));
        //when
        List<ChargeDetailDTO> chargeDetailDTOS = service.searchVehicleChargeDetails(VIN_1, 0, 10);
        //then
        Assertions.assertIterableEquals(chargeDetailDTOS, List.of(ChargeDetailMapper.INSTANCE.toDTO(entity)));
    }

    @Test
    void test_searchVehicleChargeDetails_throwsDataNotFoundException() {
        when(repository.getAllChargeDetailByVin(eq(VIN_2), any(Pageable.class))).thenReturn(Collections.emptyList());
        assertThrows(DataNotFoundException.class, () -> service.searchVehicleChargeDetails(VIN_2, 0, 10));
    }

    @AfterAll
    public static void afterAll() {
        service.resetTimer();
    }
}