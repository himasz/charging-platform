package com.dcs.repository;

import com.dcs.common.entity.ChargeDetailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeDetailRepository extends JpaRepository<ChargeDetailEntity, Long> {

    @Query(value = "SELECT * FROM charge_detail WHERE vin = :vin", nativeQuery = true)
    List<ChargeDetailEntity> getAllChargeDetailByVin(@Param("vin") String vin, Pageable pageable);

}
