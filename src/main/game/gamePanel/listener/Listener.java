package main.game.gamePanel.listener;

import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import main.game.gamePanel.listener.events.Event;

public abstract class Listener {
    Listener() {}
    static void putEvent(Event e) {
        Analyzer.addEvent(e);
    }

    public static final Vec2D mousePos = new Vec2D(100, 100);
    public static final Vec2D globalMousePos = new Vec2D(100, 100);

    public static Vec2D getMousePos() {
        return globalMousePos.clone();
    }
    public static Vec2D posToMouse(Vec2D pos) {
        return Vec2D.sub(globalMousePos, pos);
    }
}
