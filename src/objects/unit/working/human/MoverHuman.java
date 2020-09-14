package objects.unit.working.human;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import update.move.SchemaSpeeds;
import update.move.SpeedController;

import java.awt.*;

import static update.Updater.dt;

public class MoverHuman extends SpeedController {
    private final Vec2D posNow;
    private final Vec2D posEnd;
    private final Vec2D course;
    private boolean move;
    private float distToBrake;

    public MoverHuman(final Vec2D pos, SchemaSpeeds schemaSpeeds) {
        this(   pos,
                schemaSpeeds.maxSpeedForward,
                schemaSpeeds.maxSpeedBack,
                schemaSpeeds.acceleration,
                schemaSpeeds.accelToBrake);
    }

    public MoverHuman(final Vec2D pos, float maxSpeed, float maxSpeedBack, float accel, float accelToBrake) {
        super(maxSpeed, maxSpeedBack, accel, accelToBrake);
        this.posNow = pos;
        this.posEnd = new Vec2D(pos);
        this.course = new Vec2D();
    }

    public void moveTo(Vec2D pos) {
        posEnd.setXY(pos);
        course.setXY(posEnd).sub(posNow);
        if (course.getLength() > 5) {
            move = true;
            if (course.getLength() > speeds.distAccelForward + speeds.distToBrakeForward) {
                distToBrake = speeds.distToBrakeForward;
            } else {
                distToBrake = notFullWayIntegral((float) course.getLength());
            }
        }
    }

    public void update() {
        if (move) {
            course.setXY(posEnd).sub(posNow);
            if (course.getLength() > distToBrake) {
                moveForward();
            } else if (stop()) {
                move = false;
                posNow.setXY(posEnd);
            }
            super.updateSpeed();
            posNow.addAngVec(dt.scalar(getSpeed()), course.getAngle());
        }
    }

    public boolean isMove() {
        return move;
    }

    //////////////===========

    public void draw(GameDrawer drawer) {
        String[] strings = {
                "speed = " + getSpeed(),
                "Length Way = " + course.getLength(),
        };
        drawer.drawString(posNow, strings, 16, Color.YELLOW);
    }

}
