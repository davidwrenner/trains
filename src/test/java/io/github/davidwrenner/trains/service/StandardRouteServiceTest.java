/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.StandardRoutes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StandardRouteServiceTest {

    private StandardRouteService standardRouteService;

    @BeforeEach
    void setup() {
        standardRouteService = new StandardRouteService();
    }

    @Test
    void mockStandardRoutes() {
        TestingUtils.useMockServices();
        StandardRoutes standardRoutes = standardRouteService.getStandardRoutes();
        assertNotNull(standardRoutes);
        assertFalse(standardRoutes.standardRoutes().isEmpty());
    }

    @Test
    void buildUri() {
        assertTrue(standardRouteService.buildUri(Constants.STANDARD_ROUTES_URI).isPresent());
    }
}
