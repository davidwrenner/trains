/* * * * * Copyright (c) 2025 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.swing;

import io.github.davidwrenner.trains.pojo.Coordinate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MouseListener extends MouseAdapter {

    private static final Logger logger = LogManager.getLogger();

    private final TrainPanel panel;

    public MouseListener(TrainPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logger.debug("Click registered at {} {}", e.getX(), e.getY());
        this.panel.handleMousePressed(new Coordinate(e.getX(), e.getY()));
    }
}
