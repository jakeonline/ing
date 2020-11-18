package com.odsinada.ing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {IngApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , properties = {"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"})
class IngApplicationTests {

    @Test
    void contextLoads() {
    }

}
