package main.game.gamePanel.listener;

import draw.game.ServiceGameDraw;
import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import main.game.gamePanel.listener.events.Event;

public abstract class Listener {
    protected Listener() {}
    protected static void putEvent(Event e) {
        Analyzer.addEvent(e);
    }

    private static Vec2D mousePos;
    private static Vec2D globalMousePos;

    public static Vec2D getMousePos() {
        if (mousePos == null) mousePos = new Vec2D(100, 100);
        return mousePos;
    }
    public static Vec2D getGlobalMousePos() {
        if (globalMousePos == null) globalMousePos = new Vec2D(100, 100);
        return globalMousePos;
    }

    public static Vec2D posToMouse(Vec2D pos) {
        return Vec2D.sub(getGlobalMousePos(), pos);
    }

    protected void setPos(Vec2D pos) {
        getMousePos().setXY(pos);
        getGlobalMousePos().setXY(Vec2D.add(ServiceGameDraw.getCamera().firstPos, Vec2D.scalar(mousePos, ServiceGameDraw.getCamera().currentScale)));
    }
}
