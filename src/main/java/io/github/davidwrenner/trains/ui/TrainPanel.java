/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.*;
import io.github.davidwrenner.trains.service.StandardRouteService;
import io.github.davidwrenner.trains.service.StationListService;
import io.github.davidwrenner.trains.service.TrainPositionService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainPanel extends JPanel implements ActionListener {

    private static final Logger logger = LogManager.getLogger();

    private final Projector projector;

    private final TrainPositionService trainPositionService;

    private final StandardRouteService standardRouteService;

    private final StationListService stationListService;

    private final StandardRoutes standardRoutes;

    private final Stations stations;

    private final Map<String, Coordinate> stationCoordinates;

    private final Map<Integer, Coordinate> circuitIdCoordinates;

    private final Timer timer;

    private Pixel[][] grid;

    public TrainPanel() {
        super();

        this.projector = new Projector();
        this.trainPositionService = new TrainPositionService();
        this.standardRouteService = new StandardRouteService();
        this.stationListService = new StationListService();
        this.timer = new Timer(Constants.REFRESH_DELAY_MS, this);
        this.grid = new Pixel[Constants.GRID_HEIGHT_PIXELS][Constants.GRID_WIDTH_PIXELS];

        for (int i = 0; i < this.grid.length; ++i) {
            this.grid[i] = new Pixel[Constants.GRID_WIDTH_PIXELS];
        }
        emptyGrid();

        this.stations = this.stationListService.getStations();
        this.standardRoutes = this.standardRouteService.getStandardRoutes();
        this.stationCoordinates = this.projector.buildStationCoordinateMap(
                this.stations, Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);
        this.circuitIdCoordinates =
                this.projector.buildCircuitIdCoordinateMap(this.stationCoordinates, this.standardRoutes);

        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.debug("Repaint timer tick at {}", System.currentTimeMillis());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        refreshGrid();
        drawGrid((Graphics2D) g);
    }

    public void emptyGrid() {
        for (int i = 0; i < this.grid.length; ++i) {
            for (int j = 0; j < this.grid[0].length; ++j) {
                this.grid[i][j] = EmptyPixel.builder()
                        .x(j * Constants.PIXEL_WIDTH)
                        .y((this.grid.length - i - 1) * Constants.PIXEL_WIDTH)
                        .w(Constants.PIXEL_WIDTH)
                        .h(Constants.PIXEL_WIDTH)
                        .build();
            }
        }
    }

    public void refreshGrid() {
        final TrainPositions trainPositions = this.trainPositionService.getTrainPositions();

        emptyGrid();

        for (Station station : this.stations.stations()) {
            if (station == null || station.code() == null) {
                continue;
            }
            final Coordinate coordinate = this.stationCoordinates.get(station.code());
            this.grid[coordinate.y()][coordinate.x()] = StationPixel.builder()
                    .x(coordinate.x() * Constants.PIXEL_WIDTH)
                    .y((this.grid.length - coordinate.y() - 1) * Constants.PIXEL_WIDTH)
                    .w(Constants.PIXEL_WIDTH)
                    .h(Constants.PIXEL_WIDTH)
                    .build();
        }

        for (TrainPosition trainPosition : trainPositions.trainPositions()) {
            if (trainPosition == null || trainPosition.circuitId() == null || trainPosition.lineCode() == null) {
                continue;
            }
            final Coordinate coordinate = this.circuitIdCoordinates.get(trainPosition.circuitId());
            if (coordinate == null) {
                // this could be expected to occur when a train is not on a StandardRoute
                continue;
            }
            this.grid[coordinate.y()][coordinate.x()] = TrainPixel.builder()
                    .lineCode(trainPosition.lineCode())
                    .x(coordinate.x() * Constants.PIXEL_WIDTH)
                    .y((this.grid.length - coordinate.y() - 1) * Constants.PIXEL_WIDTH)
                    .w(Constants.PIXEL_WIDTH)
                    .h(Constants.PIXEL_WIDTH)
                    .build();
        }
    }

    public void drawGrid(Graphics2D g2d) {
        for (Pixel[] pixels : this.grid) {
            for (int j = 0; j < this.grid[0].length; ++j) {
                pixels[j].draw(g2d);
            }
        }
    }
}
