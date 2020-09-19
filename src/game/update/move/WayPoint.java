package game.update.move;

import lib.math.Vec2D;

public class WayPoint {
    private final Vec2D pos;

    public WayPoint(Vec2D pos) {
        this.pos = pos;
    }

    public Vec2D getPos() {
        return pos;
    }
    public void setPos(Vec2D pos) {
        this.pos.setXY(pos);
    }
}
