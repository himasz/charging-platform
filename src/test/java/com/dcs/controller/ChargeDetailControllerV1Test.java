package com.dcs.controller;


import com.dcs.BaseTest;
import com.dcs.common.dto.ChargeDetailDTO;
import com.dcs.common.error.DCSError;
import com.dcs.common.error.DCSErrorCode;
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

import static com.dcs.common.Constants.ERROR_MSG_NO_CHARGE_DETAIL_FOUND_FOR;
import static com.dcs.common.Constants.ERROR_MSG_START_TIME_S_END_TIME_S;
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
    @Sql("classpath:/liquibase/data/remove-test-table-data.sql")
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
        long startTime = END_1 + ONE_MINUTE;
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
        ResponseEntity<DCSError> creatResponseError =
                restTemplate.exchange(CREATE_CHARGE_DETAIL_URI, POST, new HttpEntity<>(CHARGE_DETAIL), DCSError.class);
        DCSError DCSError = creatResponseError.getBody();

        assertThat(DCSError).isNotNull();
        assertThat(DCSError.getDescription()).isNotEmpty();
        assertThat(DCSError.getDescription()).contains(String.format(ERROR_MSG_START_TIME_S_END_TIME_S, START_1, END_1));
        assertThat(DCSError.getCode()).isEqualTo(DCSErrorCode.INVALID_DATA_VALUE.getCode());
        assertThat(DCSError.getMessage()).isEqualTo(DCSErrorCode.INVALID_DATA_VALUE.name());
        assertThat(creatResponseError.getStatusCode()).isEqualTo(DCSErrorCode.INVALID_DATA_VALUE.getStatus());
    }

    @Test
    void whenCallingCreatingChargeDetailWithWrongBody() {
        //empty body
        ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
        ResponseEntity<DCSError> exchange =
                restTemplate.exchange(CREATE_CHARGE_DETAIL_URI, POST, new HttpEntity<>(chargeDetailDTO), DCSError.class);

        DCSError DCSError = exchange.getBody();

        assertThat(DCSError).isNotNull();
        assertThat(DCSError.getDescription()).isNotEmpty();
        assertThat(DCSError.getCode()).isEqualTo(DCSErrorCode.NOT_FOUND_BODY_FIELD.getCode());
        assertThat(DCSError.getMessage()).isEqualTo(DCSErrorCode.NOT_FOUND_BODY_FIELD.name());
        assertThat(exchange.getStatusCode()).isEqualTo(DCSErrorCode.NOT_FOUND_BODY_FIELD.getStatus());
    }


    @Test
    void testingDataNotFoundExceptionScenario() {
        ResponseEntity<DCSError> getChargeErrorResponse =
                restTemplate.exchange(BASE_URI + NOT_IN_DB_ID, GET, null, DCSError.class);

        String anyVin = "anyVin";
        ResponseEntity<DCSError> searchByVinErrorResponse =
                restTemplate.exchange(SEARCH_CHARGE_DETAIL_URL + anyVin, GET, null, DCSError.class);

        DCSError chargeErrorResponseBody = getChargeErrorResponse.getBody();

        assertThat(chargeErrorResponseBody).isNotNull();
        assertThat(chargeErrorResponseBody.getDescription()).isNotEmpty();
        assertThat(chargeErrorResponseBody.getDescription()).contains(ERROR_MSG_NO_CHARGE_DETAIL_FOUND_FOR + NOT_IN_DB_ID);
        assertThat(chargeErrorResponseBody.getCode()).isEqualTo(DCSErrorCode.DATA_NOT_FOUND.getCode());
        assertThat(chargeErrorResponseBody.getMessage()).isEqualTo(DCSErrorCode.DATA_NOT_FOUND.name());
        assertThat(getChargeErrorResponse.getStatusCode()).isEqualTo(DCSErrorCode.DATA_NOT_FOUND.getStatus());

        DCSError searchErrorResponseBody = searchByVinErrorResponse.getBody();

        assertThat(searchErrorResponseBody).isNotNull();
        assertThat(searchErrorResponseBody.getDescription()).isNotEmpty();
        assertThat(searchErrorResponseBody.getDescription()).contains(ERROR_MSG_NO_CHARGE_DETAIL_FOUND_FOR + anyVin);
        assertThat(searchErrorResponseBody.getCode()).isEqualTo(DCSErrorCode.DATA_NOT_FOUND.getCode());
        assertThat(searchErrorResponseBody.getMessage()).isEqualTo(DCSErrorCode.DATA_NOT_FOUND.name());
        assertThat(searchByVinErrorResponse.getStatusCode()).isEqualTo(DCSErrorCode.DATA_NOT_FOUND.getStatus());
    }
}