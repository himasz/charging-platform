package com.dcs;


import com.dcs.common.dto.ChargeDetailDTO;

import java.time.Duration;

public class TestData {
    public static long ID_1 = 1L;
    public static long ID_2 = 2L;
    public static long NOT_IN_DB_ID = 135L;
    public static double PRICE_1 = 130.5;
    public static double PRICE_2 = 120.5;
    public static String VIN_1 = "myVin1";
    public static String VIN_2 = "myVin2";
    public static String VIN_3 = "myVin3";
    public static final long ONE_MINUTES = Duration.ofMinutes(1).toMillis();
    public static final long TEN_MINUTES = Duration.ofMinutes(10).toMillis();
    public static long START_1 = System.currentTimeMillis();
    public static long END_1 = START_1 + ONE_MINUTES;
    public static final ChargeDetailDTO CHARGE_DETAIL = new ChargeDetailDTO(ID_1, VIN_1, START_1, END_1, PRICE_1);

}
