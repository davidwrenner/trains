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

    StationPixel(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = Color.WHITE;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(this.color);
        g2d.fillRect(this.x, this.y, this.w, this.h);
    }
}
