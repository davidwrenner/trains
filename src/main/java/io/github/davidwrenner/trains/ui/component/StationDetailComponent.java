/* * * * * Copyright (c) 2025 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.component;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class StationDetailComponent implements Component {

    private final String name;
    private final String linesServed;
    private final String address;

    StationDetailComponent(StationDetailBuilder builder) {
        this.name = builder.name;
        this.linesServed = builder.linesServed;
        this.address = builder.address;
    }

    public static StationDetailBuilder builder() {
        return new StationDetailBuilder();
    }

    public static class StationDetailBuilder {

        private String name;
        private String linesServed;
        private String address;

        StationDetailBuilder() {}

        public StationDetailBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StationDetailBuilder linesServed(String linesServed) {
            this.linesServed = linesServed;
            return this;
        }

        public StationDetailBuilder address(String address) {
            this.address = address;
            return this;
        }

        public StationDetailComponent build() {
            return new StationDetailComponent(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.SERIF, Font.PLAIN, 10));

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("Station", this.name);
        details.put("Lines", this.linesServed);
        details.put("City", this.address);

        final int padding = 15;
        int yOffset = 25;

        for (Map.Entry<String, Object> e : details.entrySet()) {
            g2d.drawString(String.format("%-9s %-20s", e.getKey() + ":", e.getValue()), padding, yOffset);
            yOffset += 15;
        }
    }
}
