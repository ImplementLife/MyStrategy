package main.game.gamePanel.listener;

import game.draw.ServiceGameDraw;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener extends Listener implements java.awt.event.MouseWheelListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int i = e.getWheelRotation();
        if (i < 0) ServiceGameDraw.getCamera().upScale();
        else ServiceGameDraw.getCamera().downScale();
    }
}
