/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.davidwrenner.trains.config.Constants;
import java.awt.*;

@JsonDeserialize(using = LineCodeDeserializer.class)
public enum LineCode {
    RD,
    YL,
    GR,
    BL,
    OR,
    SV,
    NULL;

    public Color toColor() {
        return switch (this) {
            case RD -> Color.RED;
            case YL -> Color.YELLOW;
            case GR -> Color.GREEN;
            case BL -> Constants.BRIGHTER_BLUE;
            case OR -> Constants.DARKER_ORANGE;
            case SV -> Constants.SILVER;
            case NULL -> Color.BLACK;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case RD -> "Red";
            case YL -> "Yellow";
            case GR -> "Green";
            case BL -> "Blue";
            case OR -> "Orange";
            case SV -> "Silver";
            case NULL -> "-";
        };
    }
}
