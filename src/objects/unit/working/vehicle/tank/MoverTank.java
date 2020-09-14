package objects.unit.working.vehicle.tank;

import draw.drawer.GameDrawer;
import lib.math.Angle;
import lib.math.Vec2D;
import objects.unit.working.vehicle.DBT;
import update.move.SpeedController;

import java.awt.*;

import static update.Updater.dt;

public class MoverTank extends SpeedController {
    private final Vec2D posNow;
    private final Vec2D posEnd;
    private final Vec2D course;

    private final DBT dbt;

    private boolean move, moveBack, rotate, toBrake;
    private float distToBrake;
    private double scalar;

    public MoverTank(final DBT dbt, final Vec2D pos, float maxSpeed, float maxSpeedBack, float accel, float accelToBrake) {
        super(maxSpeed, maxSpeedBack, accel, accelToBrake);
        this.posNow = pos;
        this.posEnd = new Vec2D(pos);
        this.course = new Vec2D();
        this.dbt = dbt;
    }

    public void moveTo(Vec2D pos) {
        posEnd.setXY(pos);
        course.setXY(posEnd).sub(posNow);
        float angleRotate = (float) course.getAngle();
        scalar = Vec2D.scalarProd(Vec2D.newAngVec(dbt.getAngleBody()), course);
        if (Math.PI / 2 < scalar && course.getLength() < 400) {
            moveBack = true;
            if (course.getLength() > speeds.distAccelBack + speeds.distToBrakeBack) {
                distToBrake = speeds.distToBrakeBack;
            } else distToBrake = notFullWayIntegral(course.getLength());
            angleRotate = Angle.valid(angleRotate - Math.PI);
        } else if (course.getLength() > 5) {
            moveBack = false;
            if (course.getLength() > speeds.distAccelForward + speeds.distToBrakeForward) {
                distToBrake = speeds.distToBrakeForward;
            } else distToBrake = notFullWayIntegral(course.getLength());
        }
        if (move) toBrake = true;
        move = true;
        rotate = true;
        dbt.rotateBody(angleRotate);
    }

    public void update() {
        if (toBrake) {
            if (stop()) {
                moveTo(posEnd);
                toBrake = false;
            }
            super.updateSpeed();
            posNow.addAngVec(dt.scalar(getSpeed()), dbt.getAngleBody());
        } else if (rotate) {
            dbt.update();
            rotate = dbt.isBodyRotate();
        } else if (move) {
            course.setXY(posEnd).sub(posNow);
            if (course.getLength() > distToBrake) {
                if (!moveBack) moveForward();
                else moveBack();
            } else if (stop()) {
                move = false;
                moveBack = false;
                posNow.setXY(posEnd);
            }
            super.updateSpeed();
            posNow.addAngVec(dt.scalar(getSpeed()), dbt.getAngleBody());
        }
    }

    public boolean isMove() {
        return move;
    }

    public void draw(GameDrawer drawer) {
        String[] text = {
                "toBrake = " + toBrake,
                "rotate = " + rotate,
                "move = " + move,
                "scalar = " + scalar,
        };
        drawer.drawString(posNow.clone().subY(100), text, 20, Color.BLACK);
    }
}
