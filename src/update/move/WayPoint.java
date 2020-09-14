package update.move;

import lib.math.Vec2D;
import main.game.scripts.Command;

import java.util.LinkedList;
import java.util.Queue;

public class WayPoint {
    private final Vec2D pos;
    private final Queue<Command> commands;

    public WayPoint(Vec2D pos) {
        this.pos = pos;
        commands = new LinkedList<>();
    }

    public Vec2D getPos() {
        return pos;
    }
    public void setPos(Vec2D pos) {
        this.pos.setXY(pos);
    }
}
