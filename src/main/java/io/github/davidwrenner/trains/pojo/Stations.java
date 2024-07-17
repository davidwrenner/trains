/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Stations(@JsonProperty("Stations") List<Station> stations) {}
