/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.swing;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.pojo.*;
import io.github.davidwrenner.trains.service.ProjectionService;
import io.github.davidwrenner.trains.service.StandardRouteService;
import io.github.davidwrenner.trains.service.StationListService;
import io.github.davidwrenner.trains.service.TrainPositionService;
import io.github.davidwrenner.trains.ui.component.StationDetailComponent;
import io.github.davidwrenner.trains.ui.component.TrainDetailComponent;
import io.github.davidwrenner.trains.ui.pixel.EmptyPixel;
import io.github.davidwrenner.trains.ui.pixel.Pixel;
import io.github.davidwrenner.trains.ui.pixel.StationPixel;
import io.github.davidwrenner.trains.ui.pixel.TrainPixel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainPanel extends JPanel implements ActionListener {

    private static final Logger logger = LogManager.getLogger();

    private final ProjectionService projectionService;

    private final TrainPositionService trainPositionService;

    private final StandardRouteService standardRouteService;

    private final StationListService stationListService;

    private TrainPositions trainPositions;

    private final StandardRoutes standardRoutes;

    private final Stations stations;

    private final Map<String, Coordinate> stationCoordinates;

    private final Map<Integer, Coordinate> circuitIdCoordinates;

    private final Timer timer;

    private Optional<TrainPosition> userSelectedTrain;

    private Optional<Station> userSelectedStation;

    private Pixel[][] grid;

    public TrainPanel() {
        super();

        this.projectionService = new ProjectionService();
        this.trainPositionService = new TrainPositionService();
        this.standardRouteService = new StandardRouteService();
        this.stationListService = new StationListService();
        this.timer = new Timer(Constants.REFRESH_DELAY_MS, this);
        this.grid = new Pixel[Constants.GRID_HEIGHT_PIXELS][Constants.GRID_WIDTH_PIXELS];

        for (int i = 0; i < this.grid.length; ++i) {
            this.grid[i] = new Pixel[Constants.GRID_WIDTH_PIXELS];
        }
        this.emptyGrid();

        this.trainPositions = null;
        this.stations = this.stationListService.getStations();
        this.standardRoutes = this.standardRouteService.getStandardRoutes();
        this.stationCoordinates = this.projectionService.buildStationCoordinateMap(
                this.stations, Constants.GRID_WIDTH_PIXELS, Constants.GRID_HEIGHT_PIXELS);
        this.circuitIdCoordinates =
                this.projectionService.buildCircuitIdCoordinateMap(this.stationCoordinates, this.standardRoutes);

        this.userSelectedTrain = Optional.empty();
        this.userSelectedStation = Optional.empty();

        this.timer.start();
        this.refreshGrid();
        super.repaint();
    }

    public Optional<TrainPosition> getUserSelectedTrain() {
        return this.userSelectedTrain;
    }

    public Optional<Station> getUserSelectedStation() {
        return this.userSelectedStation;
    }

    public Pixel[][] getGrid() {
        return this.grid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.debug("Repaint timer tick at {}", System.currentTimeMillis());
        this.refreshGrid();
        super.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.drawGrid((Graphics2D) g);
        this.drawStationDetailPanel((Graphics2D) g);
        this.drawTrainDetailPanel((Graphics2D) g);
    }

    private void emptyGrid() {
        for (int i = 0; i < this.grid.length; ++i) {
            for (int j = 0; j < this.grid[0].length; ++j) {
                this.grid[i][j] = EmptyPixel.builder()
                        .x(this.scalePixelsToPx(new Coordinate(j, i)).x())
                        .y(this.scalePixelsToPx(new Coordinate(j, i)).y())
                        .w(Constants.PIXEL_WIDTH_PX)
                        .h(Constants.PIXEL_HEIGHT_PX)
                        .build();
            }
        }
    }

    private void refreshGrid() {
        this.trainPositions = this.trainPositionService.getTrainPositions();
        this.emptyGrid();

        for (Station station : this.stations.stations()) {
            if (station == null || station.code() == null) {
                continue;
            }
            final Coordinate coordinate = this.stationCoordinates.get(station.code());
            this.grid[coordinate.y()][coordinate.x()] = StationPixel.builder()
                    .x(this.scalePixelsToPx(coordinate).x())
                    .y(this.scalePixelsToPx(coordinate).y())
                    .w(Constants.PIXEL_WIDTH_PX)
                    .h(Constants.PIXEL_HEIGHT_PX)
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
                    .x(this.scalePixelsToPx(coordinate).x())
                    .y(this.scalePixelsToPx(coordinate).y())
                    .w(Constants.PIXEL_WIDTH_PX)
                    .h(Constants.PIXEL_HEIGHT_PX)
                    .build();
        }
    }

    private void drawGrid(Graphics2D g2d) {
        for (Pixel[] pixels : this.grid) {
            for (int j = 0; j < this.grid[0].length; ++j) {
                pixels[j].draw(g2d);
            }
        }
    }

    private void drawTrainDetailPanel(Graphics2D g2d) {
        if (this.userSelectedTrain.isEmpty() || this.userSelectedTrain.get().lineCode() == null) {
            return;
        }

        final TrainPosition trainPosition = this.userSelectedTrain.get();

        final String nextStation = this.projectionService.computeNextStation(trainPosition, this.standardRoutes);

        final TrainDetailComponent component = TrainDetailComponent.builder()
                .trainNumber(trainPosition.trainNumber())
                .lineCode(trainPosition.lineCode().toString())
                .destination(this.stationCodeToName(trainPosition.destinationStationCode()))
                .nextStop(this.stationCodeToName(nextStation))
                .numCars(trainPosition.carCount())
                .type(trainPosition.serviceType())
                .build();

        component.draw(g2d);
    }

    public void drawStationDetailPanel(Graphics2D g2d) {
        if (this.userSelectedStation.isEmpty()) {
            return;
        }

        final Station station = this.userSelectedStation.get();

        Set<LineCode> linesServed = new HashSet<>();
        this.knownAliasesOf(station).forEach(a -> {
            linesServed.add(a.lineCode1());
            linesServed.add(a.lineCode2());
            linesServed.add(a.lineCode3());
            linesServed.add(a.lineCode4());
        });

        final StationDetailComponent component = StationDetailComponent.builder()
                .name(station.name())
                .linesServed(linesServed.stream()
                        .filter(lineCode -> lineCode != LineCode.NULL)
                        .map(LineCode::toString)
                        .sorted()
                        .collect(Collectors.joining(Constants.DETAIL_DELIMITER)))
                .address(String.join(
                        Constants.DETAIL_DELIMITER,
                        station.address().city(),
                        station.address().state()))
                .build();

        component.draw(g2d);
    }

    public void handleMousePressed(Coordinate coordinate) {
        if (this.trainPositions == null || this.stations == null) {
            this.userSelectedTrain = Optional.empty();
            this.userSelectedStation = Optional.empty();
            return;
        }

        for (TrainPosition trainPosition : this.trainPositions.trainPositions()) {
            final Coordinate candidate = this.circuitIdCoordinates.get(trainPosition.circuitId());
            if (candidate == null) {
                continue;
            }

            if (this.hasApproximatelyEqualCoordinates.test(coordinate, this.scalePixelsToPx(candidate))) {
                this.userSelectedTrain = Optional.of(trainPosition);
                this.userSelectedStation = Optional.empty();
                super.repaint();
                return;
            }
        }

        for (Station station : this.stations.stations()) {
            final Coordinate candidate = this.stationCoordinates.get(station.code());
            if (candidate == null) {
                continue;
            }

            if (this.hasApproximatelyEqualCoordinates.test(coordinate, this.scalePixelsToPx(candidate))) {
                this.userSelectedStation = Optional.of(station);
                this.userSelectedTrain = Optional.empty();
                super.repaint();
                return;
            }
        }

        this.userSelectedTrain = Optional.empty();
        this.userSelectedStation = Optional.empty();
        super.repaint();
    }

    private Coordinate scalePixelsToPx(Coordinate c) {
        return new Coordinate(
                c.x() * Constants.PIXEL_WIDTH_PX, (this.grid.length - c.y() - 1) * Constants.PIXEL_HEIGHT_PX);
    }

    private final BiPredicate<Coordinate, Coordinate> hasApproximatelyEqualCoordinates =
            (c1, c2) -> c1.x() >= c2.x() - Constants.EQUALITY_TOLERANCE_PX_INCLUSIVE
                    && c1.x() <= c2.x() + Constants.PIXEL_WIDTH_PX + Constants.EQUALITY_TOLERANCE_PX_INCLUSIVE
                    && c1.y() >= c2.y() - Constants.EQUALITY_TOLERANCE_PX_INCLUSIVE
                    && c1.y() <= c2.y() + Constants.PIXEL_HEIGHT_PX + Constants.EQUALITY_TOLERANCE_PX_INCLUSIVE;

    private String stationCodeToName(String stationCode) {
        if (stationCode == null) {
            return Constants.EMPTY_DETAIL;
        }

        return this.stations.stations().stream()
                .filter(s -> stationCode.equals(s.code()))
                .map(Station::name)
                .findFirst()
                .orElse(Constants.EMPTY_DETAIL);
    }

    private List<Station> knownAliasesOf(Station station) {
        List<Station> aliases = new ArrayList<>();
        aliases.add(station);

        this.stations.stations().stream()
                .filter(s -> s.code().equals(station.stationTogether1()))
                .findFirst()
                .ifPresent(aliases::add);

        this.stations.stations().stream()
                .filter(s -> s.code().equals(station.stationTogether2()))
                .findFirst()
                .ifPresent(aliases::add);

        return aliases;
    }
}
