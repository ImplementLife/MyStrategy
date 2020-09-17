package main.game.gamePanel.listener;

import lib.math.Vec2D;

import java.awt.event.MouseEvent;

public class MouseMotionListener extends Listener implements java.awt.event.MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        setPos(new Vec2D(e.getX(), e.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setPos(new Vec2D(e.getX(), e.getY()));
    }
}
