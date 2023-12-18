package com.dcs.common.entity;

import com.dcs.common.DCSConstants;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = DCSConstants.CHARGE_DETAIL_TBL)
public class ChargeDetailEntity {
    @Id
    private Long id;
    private String vin;
    private long startTime;
    private long endTime;
    private double price;
}
