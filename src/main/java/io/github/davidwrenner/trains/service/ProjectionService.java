/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import io.github.davidwrenner.trains.pojo.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectionService {

    public Map<Integer, Coordinate> buildCircuitIdCoordinateMap(
            Map<String, Coordinate> stationCoordinates, StandardRoutes standardRoutes) {

        if (standardRoutes == null || stationCoordinates == null) {
            return Map.of();
        }

        Map<Integer, Coordinate> circuitIdToCoordinate = new HashMap<>();
        standardRoutes
                .standardRoutes()
                .forEach(route ->
                        computeApproximateCircuitCoordinates(circuitIdToCoordinate, stationCoordinates, route));
        return circuitIdToCoordinate;
    }

    public void computeApproximateCircuitCoordinates(
            Map<Integer, Coordinate> circuitIdCoordinates,
            Map<String, Coordinate> stationCoordinates,
            StandardRoute standardRoute) {

        int fromStationIdx = -1;
        int toStationIdx = -1;

        int i = 0;
        while (i < standardRoute.trackCircuits().size()) {
            TrackCircuit trackCircuit = standardRoute.trackCircuits().get(i);
            if (trackCircuit.stationCode() == null) {
                ++i;
                continue;
            }
            toStationIdx = i;

            if (fromStationIdx >= 0) {
                Coordinate fromStationCoordinate = stationCoordinates.get(
                        standardRoute.trackCircuits().get(fromStationIdx).stationCode());
                Coordinate toStationCoordinate = stationCoordinates.get(
                        standardRoute.trackCircuits().get(toStationIdx).stationCode());

                if (fromStationCoordinate == null || toStationCoordinate == null) {
                    ++i;
                    continue;
                }

                int j = fromStationIdx;
                while (j < toStationIdx) {
                    final double fromX = fromStationCoordinate.x();
                    final double fromY = fromStationCoordinate.y();
                    final double toX = toStationCoordinate.x();
                    final double toY = toStationCoordinate.y();
                    final double diffX = toX - fromX;
                    final double diffY = toY - fromY;
                    final int numCircuits = toStationIdx - fromStationIdx + 1;

                    final Coordinate approximateCircuitLocation =
                            new Coordinate((int) (fromX + ((double) j - fromStationIdx) / numCircuits * diffX), (int)
                                    (fromY + ((double) j - fromStationIdx) / numCircuits * diffY));

                    trackCircuit = standardRoute.trackCircuits().get(j++);
                    circuitIdCoordinates.put(trackCircuit.circuitId(), approximateCircuitLocation);
                }

                circuitIdCoordinates.putIfAbsent(
                        standardRoute.trackCircuits().get(fromStationIdx).circuitId(), fromStationCoordinate);
                circuitIdCoordinates.put(
                        standardRoute.trackCircuits().get(toStationIdx).circuitId(), toStationCoordinate);
            }
            fromStationIdx = toStationIdx;
            ++i;
        }
    }

    public Map<String, Coordinate> buildStationCoordinateMap(Stations stations, int width, int height) {

        List<Station> stationList = stations.stations();
        double minLat = Double.MAX_VALUE;
        double maxLat = -1 * Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLon = -1 * Double.MAX_VALUE;

        for (Station station : stationList) {
            final double lat = station.lat();
            final double lon = station.lon();

            if (lat > maxLat) {
                maxLat = lat;
            }
            if (lat < minLat) {
                minLat = lat;
            }
            if (lon > maxLon) {
                maxLon = lon;
            }
            if (lon < minLon) {
                minLon = lon;
            }
        }

        Map<String, Coordinate> result = new HashMap<>();
        final double latSpan = maxLat - minLat;
        final double lonSpan = maxLon - minLon;

        for (Station station : stationList) {
            final double lonDiff = station.lon() - minLon;
            final double latDiff = station.lat() - minLat;
            final int x = (int) (lonDiff / lonSpan * (width - 2));
            final int y = (int) (latDiff / latSpan * (height - 2));

            result.put(station.code(), new Coordinate(x, y));
        }

        return result;
    }

    public String computeNextStation(TrainPosition trainPosition, StandardRoutes standardRoutes) {
        if (standardRoutes == null
                || trainPosition.destinationStationCode() == null
                || trainPosition.circuitId() == null) {
            return null;
        }

        Optional<StandardRoute> route = standardRoutes.standardRoutes().stream()
                .filter(r -> this.isCircuitIdInRoute(trainPosition.circuitId(), r))
                .findFirst();

        if (route.isEmpty()) {
            return null;
        }

        if (this.iterateForward(trainPosition, route.get())) {
            return this.computeNextStation(trainPosition, route.get().trackCircuits());
        } else {
            return this.computeNextStation(
                    trainPosition, route.get().trackCircuits().reversed());
        }
    }

    private boolean isCircuitIdInRoute(Integer trackCircuit, StandardRoute standardRoute) {
        for (TrackCircuit tc : standardRoute.trackCircuits()) {
            if (trackCircuit.equals(tc.circuitId())) {
                return true;
            }
        }
        return false;
    }

    private boolean iterateForward(TrainPosition trainPosition, StandardRoute standardRoute) {
        String destinationCode = trainPosition.destinationStationCode();
        for (TrackCircuit tc : standardRoute.trackCircuits()) {
            if (destinationCode.equals(tc.stationCode())) {
                return false;
            } else if (trainPosition.circuitId().equals(tc.circuitId())) {
                return true;
            }
        }
        return false;
    }

    private String computeNextStation(TrainPosition trainPosition, List<TrackCircuit> trackCircuits) {
        if (trackCircuits == null) {
            return null;
        }

        boolean hasIterationReachedPosition = false;

        for (TrackCircuit tc : trackCircuits) {
            if (trainPosition.circuitId().equals(tc.circuitId())) {
                hasIterationReachedPosition = true;
            }

            if (hasIterationReachedPosition && tc.stationCode() != null) {
                return tc.stationCode();
            }
        }

        return null;
    }
}
