/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains;

import io.github.davidwrenner.trains.config.Constants;
import io.github.davidwrenner.trains.ui.swing.MouseListener;
import io.github.davidwrenner.trains.ui.swing.TrainFrame;
import io.github.davidwrenner.trains.ui.swing.TrainPanel;
import java.awt.*;
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
        TrainPanel panel = new TrainPanel();
        panel.setName("TrainPanel");
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setPreferredSize(new Dimension(Constants.PANEL_WIDTH_PX, Constants.PANEL_HEIGHT_PX));
        panel.addMouseListener(new MouseListener(panel));

        frame = new TrainFrame();
        frame.setTitle("Trains");
        frame.setBackground(Constants.DARKER_GREY);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
