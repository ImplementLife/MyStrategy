package objects.unit.working.move;

import lib.math.Vec2D;

public class Position {

    private Vec2D pos;
    private float angle;

    public Position() {}
    public Position(Vec2D pos, float angle) {
        this.pos = pos;
        this.angle = angle;
    }

    public Vec2D getPos() {
        return pos;
    }
    public void setPos(Vec2D pos) {
        this.pos = pos;
    }

    public float getAngle() {
        return angle;
    }
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
