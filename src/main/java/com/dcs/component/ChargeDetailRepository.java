package com.dcs.component;

import com.dcs.common.entity.ChargeDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
interface ChargeDetailRepository extends JpaRepository<ChargeDetailEntity, Long> {

    @Query("SELECT chargeDetail FROM ChargeDetailEntity chargeDetail where chargeDetail.vin =:vin")
    List<ChargeDetailEntity> getAllChargeDetailByVin(@Param("vin") String vin, Pageable pageable);

}
