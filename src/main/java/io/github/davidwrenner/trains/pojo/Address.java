/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Address(
        @JsonProperty("City") String city,
        @JsonProperty("State") String state,
        @JsonProperty("Street") String street,
        @JsonProperty("Zip") String zip) {}
