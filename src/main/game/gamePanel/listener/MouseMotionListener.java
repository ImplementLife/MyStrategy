package main.game.gamePanel.listener;

import draw.game.ServiceGameDraw;
import lib.math.Vec2D;

import java.awt.event.MouseEvent;

public class MouseMotionListener extends Listener implements java.awt.event.MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos.setXY(e.getX(), e.getY());
        globalMousePos.setXY(Vec2D.add(ServiceGameDraw.getCamera().firstPos, Vec2D.scalar(mousePos, ServiceGameDraw.getCamera().currentScale)));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.setXY(e.getX(), e.getY());
        globalMousePos.setXY(Vec2D.add(ServiceGameDraw.getCamera().firstPos, Vec2D.scalar(mousePos, ServiceGameDraw.getCamera().currentScale)));
    }

}
