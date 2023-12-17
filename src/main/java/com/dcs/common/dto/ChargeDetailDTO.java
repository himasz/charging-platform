package com.dcs.common.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ChargeDetailDTO {
    @NotNull
    private Long id;
    @NotNull
    private String vin;
    @NotNull
    private long startTime;
    @NotNull
    private long endTime;
    @NotNull
    private double price;
}
