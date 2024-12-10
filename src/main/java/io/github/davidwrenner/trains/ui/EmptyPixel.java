/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui;

import io.github.davidwrenner.trains.config.Constants;
import java.awt.Color;
import java.awt.Graphics2D;

public final class EmptyPixel implements Pixel {

    private final int x;
    private final int y;
    private final int h;
    private final int w;
    private final Color color;

    EmptyPixel(EmptyPixelBuilder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.w = builder.w;
        this.h = builder.h;
        this.color = Constants.DARKER_GREY;
    }

    public static EmptyPixelBuilder builder() {
        return new EmptyPixelBuilder();
    }

    public static class EmptyPixelBuilder {

        private int x;
        private int y;
        private int w;
        private int h;

        EmptyPixelBuilder() {}

        public EmptyPixelBuilder x(int x) {
            this.x = x;
            return this;
        }

        public EmptyPixelBuilder y(int y) {
            this.y = y;
            return this;
        }

        public EmptyPixelBuilder w(int w) {
            this.w = w;
            return this;
        }

        public EmptyPixelBuilder h(int h) {
            this.h = h;
            return this;
        }

        public EmptyPixel build() {
            return new EmptyPixel(this);
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, this.w, this.h);
    }
}
