package com.dcs.common.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrencySumDTO{
    @NotNull
    Double sum;
}
