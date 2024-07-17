/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.TrainPositions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainPositionServiceTest {

    private TrainPositionService trainPositionService;

    @BeforeEach
    void setup() {
        trainPositionService = new TrainPositionService();
    }

    @Test
    void mockTrainPositions() {
        TestingUtils.useMockServices();
        TrainPositions trainPositions = trainPositionService.getTrainPositions();
        assertNotNull(trainPositions);
        assertFalse(trainPositions.trainPositions().isEmpty());
    }

    @Test
    void buildUri() {
        assertTrue(trainPositionService.buildUri(Constants.TRAIN_POSITIONS_URI).isPresent());
    }
}
