/* * * * * Copyright (c) 2025 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.swing;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.Coordinate;
import io.github.davidwrenner.trains.ui.pixel.EmptyPixel;
import io.github.davidwrenner.trains.ui.pixel.StationPixel;
import io.github.davidwrenner.trains.ui.pixel.TrainPixel;
import java.awt.*;
import javax.swing.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainPanelTest {

    private TrainPanel panel;

    private final Coordinate stationCoordinate = new Coordinate(53, 284);
    private final Coordinate trainCoordinate = new Coordinate(510, 300);
    private final Coordinate emptyCoordinate = new Coordinate(1, 1);

    @BeforeEach
    void setup() {
        TestingUtils.useMockServices();
        panel = new TrainPanel();
        panel.setName("TrainPanel");
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setPreferredSize(new Dimension(Constants.PANEL_WIDTH_PX, Constants.PANEL_HEIGHT_PX));
        panel.addMouseListener(new MouseListener(panel));
    }

    @Test
    void gridHydrates() {
        assertInstanceOf(EmptyPixel.class, panel.getGrid()[1][1]);
        assertInstanceOf(TrainPixel.class, panel.getGrid()[71][60]);
        assertInstanceOf(StationPixel.class, panel.getGrid()[71][142]);
    }

    @Test
    void noSelectedStationOnLoad() {
        assertTrue(panel.getUserSelectedStation().isEmpty());
    }

    @Test
    void handleMousePressedOnStation() {
        panel.handleMousePressed(stationCoordinate);
        assertTrue(panel.getUserSelectedStation().isPresent());
    }

    @Test
    void noSelectedTrainOnLoad() {
        assertTrue(panel.getUserSelectedTrain().isEmpty());
    }

    @Test
    void handleMousePressedOnTrain() {
        panel.handleMousePressed(trainCoordinate);
        assertTrue(panel.getUserSelectedTrain().isPresent());
    }

    @Test
    void stationDetailLocksOutTrainDetail() {
        panel.handleMousePressed(trainCoordinate);

        panel.handleMousePressed(stationCoordinate);

        assertTrue(panel.getUserSelectedStation().isPresent());
        assertTrue(panel.getUserSelectedTrain().isEmpty());
    }

    @Test
    void trainDetailLocksOutStationDetail() {
        panel.handleMousePressed(stationCoordinate);

        panel.handleMousePressed(trainCoordinate);

        assertTrue(panel.getUserSelectedTrain().isPresent());
        assertTrue(panel.getUserSelectedStation().isEmpty());
    }

    @Test
    void emptyPixelLocksOutDetail() {
        panel.handleMousePressed(trainCoordinate);

        panel.handleMousePressed(emptyCoordinate);

        assertTrue(panel.getUserSelectedTrain().isEmpty());
    }
}
