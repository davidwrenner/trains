/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Station(
        @JsonProperty("Code") String code,
        @JsonProperty("Name") String name,
        @JsonProperty("StationTogether1") String stationTogether1,
        @JsonProperty("StationTogether2") String stationTogether2,
        @JsonProperty("LineCode1") LineCode lineCode1,
        @JsonProperty("LineCode2") LineCode lineCode2,
        @JsonProperty("LineCode3") LineCode lineCode3,
        @JsonProperty("LineCode4") LineCode lineCode4,
        @JsonProperty("Lat") Double lat,
        @JsonProperty("Lon") Double lon,
        @JsonProperty("Address") Address address) {}
