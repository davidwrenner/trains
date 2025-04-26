/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.Coordinate;
import io.github.davidwrenner.trains.pojo.StandardRoutes;
import io.github.davidwrenner.trains.pojo.Stations;
import io.github.davidwrenner.trains.pojo.TrainPosition;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectionServiceTest {

    private ProjectionService projectionService;

    private StationListService stationListService;
    private StandardRouteService standardRouteService;
    private TrainPositionService trainPositionService;

    @BeforeEach
    void setup() {
        TestingUtils.useMockServices();
        projectionService = new ProjectionService();

        stationListService = new StationListService();
        standardRouteService = new StandardRouteService();
        trainPositionService = new TrainPositionService();
    }

    @Test
    void buildStationCoordinateMap_Happy() {
        Stations stations = stationListService.getStations();

        Map<String, Coordinate> coordinates = projectionService.buildStationCoordinateMap(
                stations, Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);

        assertNotNull(coordinates);
        assertEquals(102, coordinates.size());
        assertEquals(new Coordinate(40, 78), coordinates.get("N07"));
        assertEquals(new Coordinate(131, 49), coordinates.get("C06"));
        assertEquals(new Coordinate(172, 88), coordinates.get("E09"));
    }

    @Test
    void buildStationCoordinateMap_Empty() {
        Map<String, Coordinate> coordinates = projectionService.buildStationCoordinateMap(
                new Stations(List.of()), Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void buildCircuitIdCoordinateMap_Happy() {
        Stations stations = stationListService.getStations();
        StandardRoutes standardRoutes = standardRouteService.getStandardRoutes();
        Map<String, Coordinate> stationCoordinates = projectionService.buildStationCoordinateMap(
                stations, Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);

        Map<Integer, Coordinate> circuitIdCoordinates =
                projectionService.buildCircuitIdCoordinateMap(stationCoordinates, standardRoutes);

        assertNotNull(circuitIdCoordinates);
        assertEquals(3170, circuitIdCoordinates.size());
        assertEquals(new Coordinate(101, 144), circuitIdCoordinates.get(13));
        assertEquals(new Coordinate(151, 68), circuitIdCoordinates.get(525));
        assertEquals(new Coordinate(189, 75), circuitIdCoordinates.get(1542));
    }

    @Test
    void buildCircuitIdCoordinateMap_NullStationCoordinates() {
        Map<Integer, Coordinate> coordinates =
                projectionService.buildCircuitIdCoordinateMap(null, new StandardRoutes(List.of()));
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void buildCircuitIdCoordinateMap_NullRoutes() {
        Map<Integer, Coordinate> coordinates = projectionService.buildCircuitIdCoordinateMap(Map.of(), null);
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }

    @Test
    void nextStopForwardIteration() {
        TrainPosition trainPosition =
                trainPositionService.getTrainPositions().trainPositions().get(84);
        StandardRoutes standardRoutes = standardRouteService.getStandardRoutes();

        assertEquals("K05", projectionService.computeNextStation(trainPosition, standardRoutes));
    }

    @Test
    void nextStopBackwardIteration() {
        TrainPosition trainPosition =
                trainPositionService.getTrainPositions().trainPositions().get(88);
        StandardRoutes standardRoutes = standardRouteService.getStandardRoutes();

        assertEquals("N06", projectionService.computeNextStation(trainPosition, standardRoutes));
    }
}
