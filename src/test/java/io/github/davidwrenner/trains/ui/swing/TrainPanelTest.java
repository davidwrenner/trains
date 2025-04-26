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
    void noSelectedTrainOnLoad() {
        assertTrue(panel.getUserSelectedTrain().isEmpty());
    }

    @Test
    void handleMousePressedOnTrain() {
        panel.handleMousePressed(new Coordinate(510, 300));
        assertTrue(panel.getUserSelectedTrain().isPresent());
    }

    @Test
    void handleMousePressedOnEmptyPixel() {
        panel.handleMousePressed(new Coordinate(1, 1));
        assertTrue(panel.getUserSelectedTrain().isEmpty());
    }
}
