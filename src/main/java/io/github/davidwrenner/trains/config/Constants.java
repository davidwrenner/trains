/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.config;

import java.awt.Color;

public class Constants {

    private Constants() {}

    // Colors
    public static final Color DARKER_ORANGE = new Color(255, 102, 0);
    public static final Color SILVER = new Color(153, 153, 153);
    public static final Color DARKER_GREY = new Color(40, 40, 40);
    public static final Color BRIGHTER_BLUE = new Color(15, 106, 255);

    // Environment variables
    public static final String API_KEY = System.getenv("trains_api_key");

    // Timing
    public static final int REFRESH_DELAY_MS = 4000; // Train position data source refreshed every 7-10s

    // Pixel maths
    public static final int PIXEL_WIDTH = 4;
    public static final int WIDTH_PX = 800;
    public static final int HEIGHT_PX = 600;
    public static final int GRID_WIDTH_PIXELS = Math.ceilDiv(WIDTH_PX, PIXEL_WIDTH);
    public static final int GRID_HEIGHT_PIXELS = Math.ceilDiv(HEIGHT_PX, PIXEL_WIDTH);

    // URIs
    public static final String TRAIN_POSITIONS_URI =
            "https://api.wmata.com/TrainPositions/TrainPositions?contentType=json";
    public static final String STANDARD_ROUTES_URI =
            "https://api.wmata.com/TrainPositions/StandardRoutes?contentType=json";
    public static final String STATIONS_URI = "https://api.wmata.com/Rail.svc/json/jStations";
}
