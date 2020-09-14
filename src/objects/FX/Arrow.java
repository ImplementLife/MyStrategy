package objects.FX;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;

import java.awt.*;

public class Arrow extends FX {
    private final Vec2D first, end, right, left;
    private boolean draw;

    public Arrow() {
        this.first = new Vec2D();
        this.end = new Vec2D();
        this.right = new Vec2D();
        this.left = new Vec2D();
    }

    public Arrow(Vec2D first, Vec2D end) {
        this();
        this.first.setXY(first);
        this.end.setXY(end);
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }
    public void setFirst(Vec2D first) {
        this.first.setXY(first);
    }
    public void setEnd(Vec2D end) {
        this.end.setXY(end);
    }

    @Override
    public void draw(GameDrawer drawer) {
        if (draw) {
            float angle = (float) Vec2D.getAngle(first, end);
            right.setAngVec(end, 25, angle -2.8);
            left.setAngVec(end, 25, angle +2.8);

            drawer.drawLine(first, end, 2, Color.BLACK);
            drawer.drawLine(end, right, 2, Color.BLACK);
            drawer.drawLine(end, left, 2, Color.BLACK);
        }
    }

}
