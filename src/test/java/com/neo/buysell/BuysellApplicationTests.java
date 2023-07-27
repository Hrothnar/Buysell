package com.neo.buysell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BuysellApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuysellApplicationTests.class);

    @Test
    void contextLoads() {
        LOGGER.info("Some info in {} class", BuysellApplicationTests.class.getSimpleName());
        Assertions.assertEquals(4, 5 - 1);
    }

}
