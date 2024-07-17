/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.config.FeatureToggles;
import io.github.davidwrenner.trains.pojo.Stations;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

public final class StationListService extends TrainService {

    public Stations getStations() {
        return FeatureToggles.USING_MOCK_STATIONS ? getMockStations() : getLiveStations();
    }

    private Stations getLiveStations() {
        Optional<URI> uri = buildUri(Constants.STATIONS_URI);
        if (uri.isEmpty()) return emptyStations();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri.get())
                .header("api_key", Constants.API_KEY)
                .build();

        Optional<Stations> stations = doHttpRequest(request, 200, Stations.class);
        return stations.orElseGet(this::emptyStations);
    }

    private Stations getMockStations() {
        Optional<Stations> stations = readAndUnmarshal("mock/stations.txt", Stations.class);
        return stations.orElseGet(this::emptyStations);
    }

    private Stations emptyStations() {
        return new Stations(List.of());
    }
}
