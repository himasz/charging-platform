package com.dcs.mapper;

import com.dcs.TestData;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ChargeDetailMapperTest extends TestData {


    @Test
    void test_toEntity() {
        ChargeDetailDTO detailDTO = new ChargeDetailDTO(ID_1, VIN_1, START_1, END_1, PRICE_1);
        ChargeDetailEntity chargeDetailEntity = ChargeDetailMapper.INSTANCE.toEntity(detailDTO);
        Assertions.assertEquals(ID_1, chargeDetailEntity.getId());
        Assertions.assertEquals(VIN_1, chargeDetailEntity.getVin());
        Assertions.assertEquals(END_1, chargeDetailEntity.getEndTime());
        Assertions.assertEquals(START_1, chargeDetailEntity.getStartTime());
        Assertions.assertEquals(PRICE_1, chargeDetailEntity.getPrice());
    }

    @Test
    void test_toDTO() {
        ChargeDetailEntity chargeDetailEntity = new ChargeDetailEntity(ID_1, VIN_1, START_1, END_1, PRICE_1);
        ChargeDetailDTO chargeDetailDTO = ChargeDetailMapper.INSTANCE.toDTO(chargeDetailEntity);
        Assertions.assertEquals(ID_1, chargeDetailDTO.getId());
        Assertions.assertEquals(VIN_1, chargeDetailDTO.getVin());
        Assertions.assertEquals(END_1, chargeDetailDTO.getEndTime());
        Assertions.assertEquals(START_1, chargeDetailDTO.getStartTime());
        Assertions.assertEquals(PRICE_1, chargeDetailDTO.getPrice());
    }

    @Test
    void test_toDTOList() {
        ChargeDetailEntity chargeDetailEntity = new ChargeDetailEntity(ID_1, VIN_1, START_1, END_1, PRICE_1);
        List<ChargeDetailDTO> chargeDetailDTOS = ChargeDetailMapper.INSTANCE.toDTOList(List.of(chargeDetailEntity));
        Assertions.assertEquals(1, chargeDetailDTOS.size());
        Assertions.assertTrue(chargeDetailDTOS.contains(ChargeDetailMapper.INSTANCE.toDTO(chargeDetailEntity)));
    }

}