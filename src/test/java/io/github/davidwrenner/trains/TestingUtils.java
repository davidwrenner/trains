/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains;

import io.github.davidwrenner.trains.config.FeatureToggles;

public class TestingUtils {

    public static void useMockServices() {
        FeatureToggles.USING_MOCK_STANDARD_ROUTES = true;
        FeatureToggles.USING_MOCK_STATIONS = true;
        FeatureToggles.USING_MOCK_TRAIN_POSITIONS = true;
    }
}
