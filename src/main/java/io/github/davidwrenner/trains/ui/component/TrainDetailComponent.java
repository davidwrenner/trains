/* * * * * Copyright (c) 2025 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.component;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrainDetailComponent implements Component {

    private final String trainNumber;
    private final String lineCode;
    private final String destination;
    private final String nextStop;
    private final Integer numCars;
    private final String type;

    TrainDetailComponent(TrainDetailBuilder builder) {
        this.trainNumber = builder.trainNumber;
        this.lineCode = builder.lineCode;
        this.destination = builder.destination;
        this.nextStop = builder.nextStop;
        this.numCars = builder.numCars;
        this.type = builder.type;
    }

    public static TrainDetailBuilder builder() {
        return new TrainDetailBuilder();
    }

    public static class TrainDetailBuilder {

        private String trainNumber;
        private String lineCode;
        private String destination;
        private String nextStop;
        private Integer numCars;
        private String type;

        TrainDetailBuilder() {}

        public TrainDetailBuilder trainNumber(String trainNumber) {
            this.trainNumber = trainNumber;
            return this;
        }

        public TrainDetailBuilder lineCode(String lineCode) {
            this.lineCode = lineCode;
            return this;
        }

        public TrainDetailBuilder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public TrainDetailBuilder nextStop(String nextStop) {
            this.nextStop = nextStop;
            return this;
        }

        public TrainDetailBuilder numCars(Integer numCars) {
            this.numCars = numCars;
            return this;
        }

        public TrainDetailBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TrainDetailComponent build() {
            return new TrainDetailComponent(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.SERIF, Font.PLAIN, 10));

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("Train", this.trainNumber);
        details.put("Line", this.lineCode);
        details.put("Dest", this.destination);
        details.put("Next", this.nextStop);
        details.put("Cars", this.numCars);
        details.put("Type", this.type);

        final int padding = 15;
        int yOffset = 25;

        for (Map.Entry<String, Object> e : details.entrySet()) {
            g2d.drawString(String.format("%-7s %-20s", e.getKey() + ":", e.getValue()), padding, yOffset);
            yOffset += 15;
        }
    }
}
