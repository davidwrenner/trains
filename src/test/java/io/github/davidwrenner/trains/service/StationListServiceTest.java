/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.Stations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StationListServiceTest {

    private StationListService stationListService;

    @BeforeEach
    void setup() {
        stationListService = new StationListService();
    }

    @Test
    void mockStations() {
        TestingUtils.useMockServices();
        Stations stations = stationListService.getStations();
        assertNotNull(stations);
        assertFalse(stations.stations().isEmpty());
    }

    @Test
    void buildUri() {
        assertTrue(stationListService.buildUri(Constants.STATIONS_URI).isPresent());
    }
}
