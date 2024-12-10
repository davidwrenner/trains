/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.ui.TrainFrame;
import io.github.davidwrenner.trains.ui.TrainPanel;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static final Logger logger = LogManager.getLogger();

    private TrainFrame frame;

    Application() {
        initializeUI();
    }

    public static void main(String[] args) {
        if (Constants.API_KEY == null) {
            logger.error("Api key is required");
            return;
        }
        SwingUtilities.invokeLater(Application::new);
    }

    public TrainFrame getFrame() {
        return this.frame;
    }

    private void initializeUI() {
        frame = new TrainFrame();
        frame.setTitle("Trains");
        frame.setBackground(Constants.DARKER_GREY);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new TrainPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
