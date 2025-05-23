/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains.config;

import java.awt.Color;

public class Constants {

    private Constants() {}

    public static final String EMPTY_DETAIL = "-";
    public static final String DETAIL_DELIMITER = ", ";

    // Colors
    public static final Color DARKER_ORANGE = new Color(255, 102, 0);
    public static final Color SILVER = new Color(153, 153, 153);
    public static final Color DARKER_GREY = new Color(40, 40, 40);
    public static final Color BRIGHTER_BLUE = new Color(15, 106, 255);

    // Environment variables
    public static final String API_KEY = System.getenv("trains_api_key");

    // Timing
    public static final int REFRESH_DELAY_MS = 4000; // Train position data source refreshed every 7-10s

    /* Pixel maths
    _PX -> computer pixels
    _PIXELS -> unit size of grid entities - trains, stations, etc.
    */
    public static final int PIXEL_WIDTH_PX = 4;
    public static final int PIXEL_HEIGHT_PX = PIXEL_WIDTH_PX;
    public static final int PANEL_WIDTH_PX = 800;
    public static final int PANEL_HEIGHT_PX = 600;
    public static final int EQUALITY_TOLERANCE_PX_INCLUSIVE = 1;
    public static final int GRID_WIDTH_PIXELS = Math.ceilDiv(PANEL_WIDTH_PX, PIXEL_WIDTH_PX);
    public static final int GRID_HEIGHT_PIXELS = Math.ceilDiv(PANEL_HEIGHT_PX, PIXEL_HEIGHT_PX);

    // URIs
    public static final String TRAIN_POSITIONS_URI =
            "https://api.wmata.com/TrainPositions/TrainPositions?contentType=json";
    public static final String STANDARD_ROUTES_URI =
            "https://api.wmata.com/TrainPositions/StandardRoutes?contentType=json";
    public static final String STATIONS_URI = "https://api.wmata.com/Rail.svc/json/jStations";
}
