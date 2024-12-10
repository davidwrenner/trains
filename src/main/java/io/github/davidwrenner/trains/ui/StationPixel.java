/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public final class StationPixel implements Pixel {

    private final int x;
    private final int y;
    private final int h;
    private final int w;
    private final Color color;

    StationPixel(StationPixelBuilder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.w = builder.w;
        this.h = builder.h;
        this.color = Color.WHITE;
    }

    public static StationPixelBuilder builder() {
        return new StationPixelBuilder();
    }

    public static class StationPixelBuilder {

        private int x;
        private int y;
        private int w;
        private int h;

        StationPixelBuilder() {}

        public StationPixelBuilder x(int x) {
            this.x = x;
            return this;
        }

        public StationPixelBuilder y(int y) {
            this.y = y;
            return this;
        }

        public StationPixelBuilder w(int w) {
            this.w = w;
            return this;
        }

        public StationPixelBuilder h(int h) {
            this.h = h;
            return this;
        }

        public StationPixel build() {
            return new StationPixel(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, this.w, this.h);
    }
}
