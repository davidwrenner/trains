/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrainPosition(
        @JsonProperty("TrainId") String trainId,
        @JsonProperty("TrainNumber") String trainNumber,
        @JsonProperty("CarCount") Integer carCount,
        @JsonProperty("DirectionNum") Integer directionNum,
        @JsonProperty("CircuitId") Integer circuitId,
        @JsonProperty("DestinationStationCode") String destinationStationCode,
        @JsonProperty("LineCode") LineCode lineCode,
        @JsonProperty("SecondsAtLocation") Integer secondsAtLocation,
        @JsonProperty("ServiceType") String serviceType) {}
