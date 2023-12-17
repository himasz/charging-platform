package com.dcs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest extends TestData {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("charges_test")
            .withUsername("postgres")
            .withPassword("123321");

    @BeforeAll
    static void startContainer() {
        postgresContainer.start();
    }

    @AfterAll
    @Sql("classpath:/liquibase/data/remove-test-table-data.sql")
    static void stopContainer() {
        postgresContainer.stop();
    }
}
