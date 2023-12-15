package com.dcs.common.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.Duration;

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

    public static void main(String[] args) {
        long valueToAdd = Duration.ofMinutes(15).toMillis();
        long start = System.currentTimeMillis();
        long end = start + valueToAdd;
        long start2 = end + valueToAdd;
        long end2 = start2 + valueToAdd;
        long start3 = end2 + valueToAdd;
        long end3= start3 + valueToAdd;

        System.out.println(String.format("%s %s %s %s %s %s", end, start,  start2, end2, start3, end3));
    }
}
