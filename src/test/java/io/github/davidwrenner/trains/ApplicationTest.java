/* * * * * Copyright (c) 2024 David Wrenner * * * * */
package io.github.davidwrenner.trains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.*;
import javax.swing.*;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;

class ApplicationTest {

    private FrameFixture frameFixture;

    @BeforeAll
    static void setupOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void setup() {
        TestingUtils.useMockServices();
        Application app = GuiActionRunner.execute(Application::new);
        frameFixture = new FrameFixture(app.getFrame());
    }

    @AfterEach
    void tearDown() {
        frameFixture.cleanUp();
    }

    @Test
    void frameContainsPanel() {
        frameFixture.panel("TrainPanel").requireVisible();
    }

    @Test
    void graphicsAreInitialized() {
        JPanel panel = frameFixture.panel("TrainPanel").target();
        assertNotNull(panel.getGraphics());
    }

    @Test
    void mouseListenerAttached() {
        JPanel panel = frameFixture.panel("TrainPanel").target();
        assertNotNull(panel.getMouseListeners());
        assertEquals(1, panel.getMouseListeners().length);
    }
}
