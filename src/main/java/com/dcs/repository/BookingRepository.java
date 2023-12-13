package com.dcs.repository;

import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.error.exceptions.MissingDataException;
import org.springframework.stereotype.Component;

import java.util.*;

import com.dcs.common.enums.Currency;


@Component
public class BookingRepository {
    public static final String SPLITOR = ":";
    private final Map<String, ChargeDetailDTO> repository = new HashMap<>();

    private final Map<String, Set<String>> departments = new HashMap<>();

    private final Map<String, Set<String>> currencies = new HashMap<>();

    public void addOrUpdateBooking(String bookingId, ChargeDetailDTO chargeDetailDTO) {
        repository.put(bookingId, chargeDetailDTO);
        addToCache(departments, chargeDetailDTO.getDepartment(), bookingId);
        addToCache(currencies, chargeDetailDTO.getCurrency().name(), bookingId + SPLITOR + chargeDetailDTO.getPrice());
    }

    public ChargeDetailDTO getBooking(String bookingId) {
        return repository.get(bookingId);
    }

    public Double getSum(Currency currency) {
        return Optional.ofNullable(currencies.get(currency.name()))
                .orElseThrow(() -> new MissingDataException("No prices found for currency: " + currency.name()))
                .stream()
                .map(idCurrency -> Double.valueOf(idCurrency.split(SPLITOR)[1]))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public Set<String> getDepartmentBookings(String department) {
        return departments.get(department);
    }

    public Set<String> getBookingCurrencies() {
        return currencies.keySet();
    }

    public void removeBooking(String bookingId) {
        ChargeDetailDTO chargeDetailDTO = repository.get(bookingId);
        repository.remove(bookingId);
        removeFromCache(departments, chargeDetailDTO.getDepartment(), bookingId);
        removeFromCache(currencies, chargeDetailDTO.getCurrency().name(), bookingId + ":" + chargeDetailDTO.getPrice());
    }

    private <T> void removeFromCache(Map<String, Set<T>> cache, String id, T value) {
        cache.get(id).remove(value);
    }

    private <T> void addToCache(Map<String, Set<T>> cache, String id, T value) {
        Set<T> cachedList = cache.getOrDefault(id, new HashSet<>());
        cachedList.add(value);
        cache.put(id, cachedList);
    }
}
