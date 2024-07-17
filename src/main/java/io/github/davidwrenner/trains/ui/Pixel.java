/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui;

import java.awt.Graphics2D;

public sealed interface Pixel permits EmptyPixel, TrainPixel, StationPixel {

    void draw(Graphics2D g2d);
}
