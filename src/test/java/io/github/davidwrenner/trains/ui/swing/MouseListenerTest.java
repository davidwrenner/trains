/* * * * * Copyright (c) 2025 David Wrenner * * * * */
package io.github.davidwrenner.trains.ui.swing;

import static org.mockito.Mockito.*;

import io.github.davidwrenner.trains.TestingUtils;
import io.github.davidwrenner.trains.pojo.Coordinate;
import java.awt.event.MouseEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MouseListenerTest {

    private TrainPanel panelSpy;
    private MouseListener mouseListener;

    @BeforeEach
    void setup() {
        TestingUtils.useMockServices();
        panelSpy = spy(new TrainPanel());
        mouseListener = new MouseListener(panelSpy);
    }

    @Test
    void mousePressed() {
        final long now = System.currentTimeMillis();
        final int x = 1;
        final int y = 1;
        final int modifiers = 0;
        final int numClicks = 1;
        final boolean popupTrigger = false;
        final MouseEvent press = new MouseEvent(
                new TrainPanel(),
                MouseEvent.MOUSE_PRESSED,
                now,
                modifiers,
                x,
                y,
                numClicks,
                popupTrigger,
                MouseEvent.NOBUTTON);

        mouseListener.mousePressed(press);

        verify(panelSpy).handleMousePressed(new Coordinate(x, y));
        verify(panelSpy).repaint();
    }
}
