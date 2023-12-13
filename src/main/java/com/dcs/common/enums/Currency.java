package com.dcs.common.enums;

public enum Currency {
    USD("United States Dollar"),
    EUR("Euro"),
    JPY("Japanese Yen"),
    GBP("British Pound Sterling"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Chinese Yuan"),
    SEK("Swedish Krona"),
    NZD("New Zealand Dollar"),
    KRW("South Korean Won"),
    SGD("Singapore Dollar"),
    NOK("Norwegian Krone"),
    MXN("Mexican Peso"),
    INR("Indian Rupee"),
    BRL("Brazilian Real"),
    ZAR("South African Rand"),
    RUB("Russian Ruble"),
    TRY("Turkish Lira"),
    HKD("Hong Kong Dollar");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public static Currency fromString(String currencyCode) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equalsIgnoreCase(currencyCode)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("No currency with code " + currencyCode + " found.");
    }
}
