package com.dcs.repository;


import com.dcs.TestBookingData;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.enums.Currency;
import com.dcs.common.error.exceptions.MissingDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingRepositoryTest extends TestBookingData {
    private static final BookingRepository repo = new BookingRepository();

    @BeforeAll
    public static void beforeAll() {
        //Given
        repo.addOrUpdateBooking(ID_1, FIRST_BOOKING);
        repo.addOrUpdateBooking(ID_2, SECOND_BOOKING);
        repo.addOrUpdateBooking(ID_3, THIRD_BOOKING);
    }

    @Test
    void test_getBooking_retrieveCorrectValue() {
        ChargeDetailDTO booking1 = repo.getBooking(ID_1);
        Assertions.assertEquals(DESCRIPTION_1, booking1.getDescription());

        ChargeDetailDTO booking2 = repo.getBooking(ID_2);
        Assertions.assertEquals(DESCRIPTION_2, booking2.getDescription());

        Assertions.assertNull(repo.getBooking(WRONG_ID));
    }

    @Test
    void test_getSum_successful() {
        Assertions.assertEquals(PRICE_1 + PRICE_2, repo.getSum(EUR));
        Assertions.assertEquals(PRICE_3, repo.getSum(USD));
    }

    @Test
    void test_getSum_noFoundCurrencyThrowsInvalidParamException() {
        assertThrows(MissingDataException.class, () -> repo.getSum(Currency.BRL));
    }

    @Test
    void test_getDepartmentBookings() {
        Set<String> firstDepartmentBookingIds = repo.getDepartmentBookings(DEPARTMENT_1);
        Assertions.assertEquals(1, firstDepartmentBookingIds.size());
        Assertions.assertTrue(firstDepartmentBookingIds.contains(ID_1));

        Set<String> secondDepartmentBookingIds = repo.getDepartmentBookings(DEPARTMENT_2);
        Assertions.assertEquals(2, secondDepartmentBookingIds.size());
        Assertions.assertIterableEquals(Arrays.asList(ID_2, ID_3), secondDepartmentBookingIds);
    }

    @Test
    void test_getBookingCurrencies() {
        Set<String> bookingCurrencies = repo.getBookingCurrencies();
        Assertions.assertTrue(bookingCurrencies.contains(USD.name()));
        Assertions.assertTrue(bookingCurrencies.contains(EUR.name()));
    }

    @Test
    void test_removeBooking() {
        String id = "Id";
        String department = "myDep";
        Currency currency = Currency.AUD;

        //given
        repo.addOrUpdateBooking(id, createBookingDTO(DESCRIPTION_1, PRICE_1, department, currency));

        //when
        repo.removeBooking(id);

        //then
        Assertions.assertNull(repo.getBooking(id));
        Assertions.assertEquals(0, repo.getSum(currency));
        Assertions.assertIterableEquals(Collections.emptyList(), repo.getDepartmentBookings(department));
    }

}