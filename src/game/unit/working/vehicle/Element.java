package game.unit.working.vehicle;

import draw.drawer.Draw;
import draw.drawer.GameDrawer;
import lib.math.Angle;
import lib.math.Vec2D;

import java.awt.*;

public class Element {
    private final Vec2D pos;
    private final Angle angle;
    private Vec2D offset;
    private Image image;

    public Element(final Vec2D pos, Image image, float angle) {
        this.pos = pos;
        this.angle = new Angle(angle);
        this.image = image;
    }
    public Element(final Vec2D pos, Vec2D offset, Image image, float angle) {
        this(pos, image, angle);
        this.offset = offset;
    }

    public void setPos(Vec2D pos) {
        this.pos.setXY(pos);
    }

    public void setAngle(float angle) {
        this.angle.setValue(angle);
    }
    public void setAngle(Angle angle) {
        this.angle.setValue(angle);
    }

    public void draw(Draw drawer) {
        if (offset != null) drawer.drawImage(pos, image, angle, offset);
        else drawer.drawImage(pos, image, angle);
    }
}
