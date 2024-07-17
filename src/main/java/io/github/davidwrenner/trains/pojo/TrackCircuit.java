/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrackCircuit(
        @JsonProperty("SeqNum") Integer seqNum,
        @JsonProperty("CircuitId") Integer circuitId,
        @JsonProperty("StationCode") String stationCode) {}
