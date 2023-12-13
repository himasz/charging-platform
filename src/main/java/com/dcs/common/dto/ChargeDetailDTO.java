package com.dcs.common.dto;

import com.dcs.common.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "device")
public class ChargeDetailDTO {
    @NotNull
    private String description;
    @NotNull
    private double price;
    @NotNull
    private Currency currency;
    @NotNull
    @JsonProperty("subscription_start_date")
    private long subscriptionStartDate;
    @NotNull
    private String email;
    @NotNull
    private String department;
}
