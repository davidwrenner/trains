/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.config.FeatureToggles;
import io.github.davidwrenner.trains.pojo.TrainPositions;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

public final class TrainPositionService extends TrainService {

    public TrainPositions getTrainPositions() {
        return FeatureToggles.USING_MOCK_TRAIN_POSITIONS ? getMockPositions() : getLivePositions();
    }

    private TrainPositions getLivePositions() {
        Optional<URI> uri = buildUri(Constants.TRAIN_POSITIONS_URI);
        if (uri.isEmpty()) return emptyTrainPositions();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri.get())
                .header("Cache-Control", "no-cache")
                .header("api_key", Constants.API_KEY)
                .build();

        Optional<TrainPositions> trainPositions = doHttpRequest(request, 200, TrainPositions.class);
        return trainPositions.orElseGet(this::emptyTrainPositions);
    }

    private TrainPositions getMockPositions() {
        Optional<TrainPositions> trainPositions = readAndUnmarshal("mock/trainPositions.txt", TrainPositions.class);
        return trainPositions.orElseGet(this::emptyTrainPositions);
    }

    private TrainPositions emptyTrainPositions() {
        return new TrainPositions(List.of());
    }
}
