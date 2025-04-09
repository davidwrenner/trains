/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.pixel;

import io.github.davidwrenner.trains.pojo.LineCode;
import java.awt.Color;
import java.awt.Graphics2D;

public final class TrainPixel implements Pixel {

    private final int x;
    private final int y;
    private final int h;
    private final int w;
    private final Color color;

    TrainPixel(TrainPixelBuilder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.w = builder.w;
        this.h = builder.h;
        this.color = builder.lineCode.toColor();
    }

    public static TrainPixelBuilder builder() {
        return new TrainPixelBuilder();
    }

    public static class TrainPixelBuilder {

        private LineCode lineCode;
        private int x;
        private int y;
        private int w;
        private int h;

        TrainPixelBuilder() {}

        public TrainPixelBuilder lineCode(LineCode lineCode) {
            this.lineCode = lineCode;
            return this;
        }

        public TrainPixelBuilder x(int x) {
            this.x = x;
            return this;
        }

        public TrainPixelBuilder y(int y) {
            this.y = y;
            return this;
        }

        public TrainPixelBuilder w(int w) {
            this.w = w;
            return this;
        }

        public TrainPixelBuilder h(int h) {
            this.h = h;
            return this;
        }

        public TrainPixel build() {
            return new TrainPixel(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, this.w, this.h);
    }
}
