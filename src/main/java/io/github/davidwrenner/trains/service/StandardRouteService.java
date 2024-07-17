/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.config.FeatureToggles;
import io.github.davidwrenner.trains.pojo.StandardRoutes;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

public final class StandardRouteService extends TrainService {

    public StandardRoutes getStandardRoutes() {
        return FeatureToggles.USING_MOCK_STANDARD_ROUTES ? getMockStandardRoutes() : getLiveStandardRoutes();
    }

    private StandardRoutes getLiveStandardRoutes() {
        Optional<URI> uri = buildUri(Constants.STANDARD_ROUTES_URI);
        if (uri.isEmpty()) return emptyStandardRoutes();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri.get())
                .header("api_key", Constants.API_KEY)
                .build();

        Optional<StandardRoutes> standardRoutes = doHttpRequest(request, 200, StandardRoutes.class);
        return standardRoutes.orElseGet(this::emptyStandardRoutes);
    }

    private StandardRoutes getMockStandardRoutes() {
        Optional<StandardRoutes> standardRoutes = readAndUnmarshal("mock/standardRoutes.txt", StandardRoutes.class);
        return standardRoutes.orElseGet(this::emptyStandardRoutes);
    }

    private StandardRoutes emptyStandardRoutes() {
        return new StandardRoutes(List.of());
    }
}
