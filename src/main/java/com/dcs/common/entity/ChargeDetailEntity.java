package com.dcs.common.entity;

import com.dcs.common.constant.Constants;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = Constants.CHARGE_DETAIL_TBL)
public class ChargeDetailEntity {
    @Id
    private Long id;
    private String vin;
    private long startTime;
    private long endTime;
    private double price;
}
