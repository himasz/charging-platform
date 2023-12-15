//package com.dcs.controller;
//
//
//import com.dcs.TestBookingData;
//import com.dcs.common.dto.ChargeDetailDTO;
//import com.dcs.common.dto.CurrencySumDTO;
//import com.dcs.common.error.ApiError;
//import com.dcs.common.error.codes.ApiErrorCode;
//import org.assertj.core.api.Assertions;
//import org.assertj.core.api.AssertionsForClassTypes;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.http.HttpMethod.GET;
//import static org.springframework.http.HttpMethod.PUT;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class ChargeDetailControllerV1Test extends TestBookingData {
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private final String BASE_URI = "/charge/detail/v1/";
//    private final String CREATE_CHARGE_DETAIL_URI = BASE_URI + "create";
//    private final String SEARCH_CHARGE_DETAIL_URL = BASE_URI + "search/";
//
//    @Test
//    void whenAddNewBookingAndGetItCorrectly() {
////        String url = BOOKING_BASE_URL + ID_1;
////
////        ResponseEntity<Void> putBookingResponse =
////                restTemplate.exchange(url, PUT, new HttpEntity<>(BOOKING), Void.class);
////
////        assertThat(putBookingResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
////
////        ResponseEntity<ChargeDetailDTO> getBookingResponse =
////                restTemplate.exchange(url, GET, null, ChargeDetailDTO.class);
////
////        assertThat(getBookingResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
////
////        ChargeDetailDTO body = getBookingResponse.getBody();
////
////        assertThat(body).isNotNull();
////        assertThat(body.getEmail()).isEqualTo(BOOKING.getEmail());
////        assertThat(body.getPrice()).isEqualTo(BOOKING.getPrice());
////        AssertionsForClassTypes.assertThat(body.getCurrency()).isEqualTo(BOOKING.getCurrency());
////        assertThat(body.getDepartment()).isEqualTo(BOOKING.getDepartment());
////        assertThat(body.getDescription()).isEqualTo(BOOKING.getDescription());
//    }
//
//    @Test
//    void whenCallingPutBookingWithoutBody() {
//        String url = BOOKING_BASE_URL + ID_1;
//        ResponseEntity<ApiError> putBookingResponse =
//                restTemplate.exchange(url, PUT, new HttpEntity<>(new ChargeDetailDTO()), ApiError.class);
//
//        ApiError apiError = putBookingResponse.getBody();
//
//        assertThat(apiError).isNotNull();
//        assertThat(apiError.getDescription()).isNotEmpty();
//        assertThat(apiError.getCode()).isEqualTo(ApiErrorCode.MISSING_BODY_FIELD.getCode());
//        assertThat(apiError.getMessage()).isEqualTo(ApiErrorCode.MISSING_BODY_FIELD.name());
//        assertThat(putBookingResponse.getStatusCode()).isEqualTo(ApiErrorCode.MISSING_BODY_FIELD.getHttpStatus());
//    }
//
//    @Test
//    void testingDepartmentsAndCurrencyScenarios() {
//        //given
//        restTemplate.exchange(BOOKING_BASE_URL + ID_1, PUT, new HttpEntity<>(FIRST_BOOKING), Void.class);
//        restTemplate.exchange(BOOKING_BASE_URL + ID_2, PUT, new HttpEntity<>(SECOND_BOOKING), Void.class);
//        restTemplate.exchange(BOOKING_BASE_URL + ID_3, PUT, new HttpEntity<>(THIRD_BOOKING), Void.class);
//
//        //when
//        ResponseEntity<CurrencySumDTO> eurSumResponse =
//                restTemplate.exchange(CURRENCY_URL + EUR.name(), GET, null, CurrencySumDTO.class);
//
//        ResponseEntity<List> currenciesResponse =
//                restTemplate.exchange(CURRENCIES_URL, GET, null, List.class);
//
//        ResponseEntity<List> secondDepartmentResponse =
//                restTemplate.exchange(DEPARTMENT_URL + DEPARTMENT_2, GET, null, List.class);
//
//        //then
//        assertThat(eurSumResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(eurSumResponse.getBody().getSum()).isEqualTo(FIRST_BOOKING.getPrice() + SECOND_BOOKING.getPrice());
//
//        assertThat(currenciesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(currenciesResponse.getBody()).contains(EUR.name(), USD.name());
//
//        assertThat(secondDepartmentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(secondDepartmentResponse.getBody()).contains(ID_2, ID_3).doesNotContain(ID_1);
//    }
//
//    @Test
//    void missingDataException() {
////        ResponseEntity<ApiError> sumErrorResponse =
////                restTemplate.exchange(CURRENCY_URL + "any", GET, null, ApiError.class);
////
////        ResponseEntity<ApiError> departmentErrorResponse =
////                restTemplate.exchange(DEPARTMENT_URL + "any", GET, null, ApiError.class);
////
////        ApiError sumErrorResponseBody = sumErrorResponse.getBody();
////
////        assertThat(sumErrorResponseBody).isNotNull();
////        assertThat(sumErrorResponseBody.getDescription()).isNotEmpty();
////        assertThat(sumErrorResponseBody.getCode()).isEqualTo(ApiErrorCode.MISSING_DATA_VALUE.getCode());
////        assertThat(sumErrorResponseBody.getMessage()).isEqualTo(ApiErrorCode.MISSING_DATA_VALUE.name());
////        assertThat(sumErrorResponse.getStatusCode()).isEqualTo(ApiErrorCode.MISSING_DATA_VALUE.getHttpStatus());
////
////        ApiError departmentErrorResponseBody = departmentErrorResponse.getBody();
////
////        assertThat(departmentErrorResponseBody).isNotNull();
////        assertThat(departmentErrorResponseBody.getDescription()).isNotEmpty();
////        assertThat(departmentErrorResponseBody.getCode()).isEqualTo(ApiErrorCode.MISSING_DATA_VALUE.getCode());
////        assertThat(departmentErrorResponseBody.getMessage()).isEqualTo(ApiErrorCode.MISSING_DATA_VALUE.name());
////        assertThat(sumErrorResponse.getStatusCode()).isEqualTo(ApiErrorCode.MISSING_DATA_VALUE.getHttpStatus());
//    }
//
//}