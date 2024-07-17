/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.Coordinate;
import io.github.davidwrenner.trains.pojo.StandardRoutes;
import io.github.davidwrenner.trains.pojo.Stations;
import io.github.davidwrenner.trains.service.StandardRouteService;
import io.github.davidwrenner.trains.service.StationListService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectorTest {

    private Projector projector;

    @BeforeEach
    void setup() {
        projector = new Projector();
    }

    @Test
    void buildStationCoordinateMap_Happy() {
        TestingUtils.useMockServices();
        StationListService stationListService = new StationListService();
        Stations stations = stationListService.getStations();

        Map<String, Coordinate> coordinates = projector.buildStationCoordinateMap(
                stations, Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);

        assertNotNull(coordinates);
        assertEquals(102, coordinates.size());
        assertEquals(new Coordinate(40, 78), coordinates.get("N07"));
        assertEquals(new Coordinate(131, 49), coordinates.get("C06"));
        assertEquals(new Coordinate(172, 88), coordinates.get("E09"));
    }

    @Test
    void buildStationCoordinateMap_Empty() {
        Map<String, Coordinate> coordinates = projector.buildStationCoordinateMap(
                new Stations(List.of()), Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void buildCircuitIdCoordinateMap_Happy() {
        TestingUtils.useMockServices();
        StationListService stationListService = new StationListService();
        StandardRouteService standardRouteService = new StandardRouteService();
        Stations stations = stationListService.getStations();
        StandardRoutes standardRoutes = standardRouteService.getStandardRoutes();
        Map<String, Coordinate> stationCoordinates = projector.buildStationCoordinateMap(
                stations, Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);

        Map<Integer, Coordinate> circuitIdCoordinates =
                projector.buildCircuitIdCoordinateMap(stationCoordinates, standardRoutes);

        assertNotNull(circuitIdCoordinates);
        assertEquals(3170, circuitIdCoordinates.size());
        assertEquals(new Coordinate(101, 144), circuitIdCoordinates.get(13));
        assertEquals(new Coordinate(151, 68), circuitIdCoordinates.get(525));
        assertEquals(new Coordinate(189, 75), circuitIdCoordinates.get(1542));
    }

    @Test
    void buildCircuitIdCoordinateMap_NullStationCoordinates() {
        Map<Integer, Coordinate> coordinates =
                projector.buildCircuitIdCoordinateMap(null, new StandardRoutes(List.of()));
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void buildCircuitIdCoordinateMap_NullRoutes() {
        Map<Integer, Coordinate> coordinates = projector.buildCircuitIdCoordinateMap(Map.of(), null);
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }
}
