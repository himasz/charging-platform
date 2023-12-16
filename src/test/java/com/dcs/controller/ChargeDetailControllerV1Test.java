package com.dcs.controller;


import com.dcs.BaseTest;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.error.ApiError;
import com.dcs.common.error.codes.ApiErrorCode;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.dcs.common.constants.Constants.NO_CHARGE_DETAIL_FOUND_FOR;
import static com.dcs.common.constants.Constants.START_TIME_S_END_TIME_S_MESSAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChargeDetailControllerV1Test extends BaseTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URI = "/charge/detail/v1/";
    private final String CREATE_CHARGE_DETAIL_URI = BASE_URI + "create";
    private final String SEARCH_CHARGE_DETAIL_URL = BASE_URI + "search/";

    @Test
    @Order(1)
    @Sql("classpath:/liquibase/data/clear-data.sql")
    void whenAddNewChargeDetailAndGetItCorrectly() {
        ResponseEntity<Void> createResponse =
                restTemplate.exchange(CREATE_CHARGE_DETAIL_URI, POST, new HttpEntity<>(CHARGE_DETAIL), Void.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ChargeDetailDTO> getChargeDetailResponse =
                restTemplate.exchange(BASE_URI + ID_1, GET, null, ChargeDetailDTO.class);

        assertThat(getChargeDetailResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ChargeDetailDTO getBody = getChargeDetailResponse.getBody();

        assertThat(getBody).isNotNull();
        assertThat(getBody.getId()).isEqualTo(ID_1);
        assertThat(getBody.getVin()).isEqualTo(VIN_1);
        assertThat(getBody.getPrice()).isEqualTo(PRICE_1);
        assertThat(getBody.getStartTime()).isEqualTo(START_1);
        assertThat(getBody.getEndTime()).isEqualTo(END_1);

        ResponseEntity<List> searchByVinChargeDetailResponse =
                restTemplate.exchange(SEARCH_CHARGE_DETAIL_URL + VIN_1, GET, null, List.class);

        assertThat(searchByVinChargeDetailResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        List searchBody = searchByVinChargeDetailResponse.getBody();
        assertThat(searchBody.size()).isEqualTo(1);
        assertThat(searchBody.get(0).toString()).contains(VIN_1);
    }

    @Test
    @Order(2)
    void testThatSearchByVinReturnsValuesInOrder() {
        long startTime = END_1 + ONE_MINUTES;
        long endTime = END_1 + TEN_MINUTES;
        ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO(ID_2, VIN_1, startTime, endTime, PRICE_1);
        restTemplate.exchange(CREATE_CHARGE_DETAIL_URI, POST, new HttpEntity<>(chargeDetailDTO), Void.class);
        ResponseEntity<List> searchByVinChargeDetailResponse =
                restTemplate.exchange(SEARCH_CHARGE_DETAIL_URL + VIN_1, GET, null, List.class);

        assertThat(searchByVinChargeDetailResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        List searchBody = searchByVinChargeDetailResponse.getBody();
        assertThat(searchBody.size()).isEqualTo(2);
        assertThat(searchBody.get(0).toString()).contains(VIN_1);
        assertThat(searchBody.get(0).toString()).contains(String.valueOf(START_1));
        assertThat(searchBody.get(1).toString()).contains(VIN_1);
        assertThat(searchBody.get(1).toString()).contains(String.valueOf(startTime));
    }

    @Test
    @Order(3)
    void testTimeConstraintFailure() {
        ResponseEntity<ApiError> creatResponseError =
                restTemplate.exchange(CREATE_CHARGE_DETAIL_URI, POST, new HttpEntity<>(CHARGE_DETAIL), ApiError.class);
        ApiError apiError = creatResponseError.getBody();

        assertThat(apiError).isNotNull();
        assertThat(apiError.getDescription()).isNotEmpty();
        assertThat(apiError.getDescription()).contains(String.format(START_TIME_S_END_TIME_S_MESSAGE, START_1, END_1));
        assertThat(apiError.getCode()).isEqualTo(ApiErrorCode.INVALID_DATA_VALUE.getCode());
        assertThat(apiError.getMessage()).isEqualTo(ApiErrorCode.INVALID_DATA_VALUE.name());
        assertThat(creatResponseError.getStatusCode()).isEqualTo(ApiErrorCode.INVALID_DATA_VALUE.getHttpStatus());
    }

    @Test
    void whenCallingCreatingChargeDetailWithWrongBody() {
        //empty body
        ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
        ResponseEntity<ApiError> exchange =
                restTemplate.exchange(CREATE_CHARGE_DETAIL_URI, POST, new HttpEntity<>(chargeDetailDTO), ApiError.class);

        ApiError apiError = exchange.getBody();

        assertThat(apiError).isNotNull();
        assertThat(apiError.getDescription()).isNotEmpty();
        assertThat(apiError.getCode()).isEqualTo(ApiErrorCode.MISSING_BODY_FIELD.getCode());
        assertThat(apiError.getMessage()).isEqualTo(ApiErrorCode.MISSING_BODY_FIELD.name());
        assertThat(exchange.getStatusCode()).isEqualTo(ApiErrorCode.MISSING_BODY_FIELD.getHttpStatus());
    }


    @Test
    void testingDataNotFoundExceptionScenario() {
        ResponseEntity<ApiError> getChargeErrorResponse =
                restTemplate.exchange(BASE_URI + NOT_IN_DB_ID, GET, null, ApiError.class);

        String anyVin = "anyVin";
        ResponseEntity<ApiError> searchByVinErrorResponse =
                restTemplate.exchange(SEARCH_CHARGE_DETAIL_URL + anyVin, GET, null, ApiError.class);

        ApiError chargeErrorResponseBody = getChargeErrorResponse.getBody();

        assertThat(chargeErrorResponseBody).isNotNull();
        assertThat(chargeErrorResponseBody.getDescription()).isNotEmpty();
        assertThat(chargeErrorResponseBody.getDescription()).contains(NO_CHARGE_DETAIL_FOUND_FOR + NOT_IN_DB_ID);
        assertThat(chargeErrorResponseBody.getCode()).isEqualTo(ApiErrorCode.DATA_NOT_FOUND.getCode());
        assertThat(chargeErrorResponseBody.getMessage()).isEqualTo(ApiErrorCode.DATA_NOT_FOUND.name());
        assertThat(getChargeErrorResponse.getStatusCode()).isEqualTo(ApiErrorCode.DATA_NOT_FOUND.getHttpStatus());

        ApiError searchErrorResponseBody = searchByVinErrorResponse.getBody();

        assertThat(searchErrorResponseBody).isNotNull();
        assertThat(searchErrorResponseBody.getDescription()).isNotEmpty();
        assertThat(searchErrorResponseBody.getDescription()).contains(NO_CHARGE_DETAIL_FOUND_FOR + anyVin);
        assertThat(searchErrorResponseBody.getCode()).isEqualTo(ApiErrorCode.DATA_NOT_FOUND.getCode());
        assertThat(searchErrorResponseBody.getMessage()).isEqualTo(ApiErrorCode.DATA_NOT_FOUND.name());
        assertThat(searchByVinErrorResponse.getStatusCode()).isEqualTo(ApiErrorCode.DATA_NOT_FOUND.getHttpStatus());
    }

}