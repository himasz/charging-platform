package com.dcs.mapper;

import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.entity.ChargeDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface ChargeDetailMapper {
    ChargeDetailMapper INSTANCE = Mappers.getMapper( ChargeDetailMapper.class);

    ChargeDetailDTO toDTO(ChargeDetailEntity chargeDetailEntity);

    ChargeDetailEntity toEntity(ChargeDetailDTO chargeDetailDTO);

    List<ChargeDetailDTO> toDTOList(List<ChargeDetailEntity> entities);

}
