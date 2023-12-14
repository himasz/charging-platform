package com.dcs.common.entity;

import com.dcs.common.constants.ApiConstants;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = ApiConstants.CHARGE)
public class ChargeDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private long startTime;
    private long endTime;
    private double price;

}
