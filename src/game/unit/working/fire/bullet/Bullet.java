package game.unit.working.fire.bullet;

import draw.drawer.Draw;
import game.objects.GameObject;
import lib.math.Angle;
import lib.math.Vec2D;
import game.objects.managers.GameObjectTypes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static game.update.Updater.dt;

public class Bullet extends GameObject {
    //==========     Static     =============//
    private static final GameObjectTypes TYPE = GameObjectTypes.BULLET;
    private static HashMap<String, Image> images;
    static {
        images = new HashMap<>();
        putImage("resource/bullet/trace_10.png");
    }

    private static void putImage(String path) {
        images.put(path, new ImageIcon(path).getImage());
    }

    //=======================================//
    private final Vec2D pos;
    private final Vec2D posNow;
    private final Vec2D posEnd;

    private final Angle angle;
    private Image image;
    private Runnable removeExe;

    private float speed;

    //=======================================//
    public Bullet(Vec2D pos, Vec2D posEnd) {
        super(TYPE);
        this.pos = new Vec2D(pos);
        this.posNow = new Vec2D(pos);
        this.posEnd = new Vec2D(posEnd);
        this.angle = new Angle(Vec2D.getAngle(pos, posEnd));
        this.speed = 1500;
    }
    public Bullet(Vec2D pos, Vec2D posEnd, String name) {
        this(pos, posEnd);
        if (name != null) this.image = images.get(name);
    }
    public Bullet(Vec2D pos, Vec2D posEnd, String name, float speed) {
        this(pos, posEnd, name);
        this.speed = speed;
    }

    //=======================================//
    public Vec2D getFirstPos() {
        return pos;
    }

    public void setRemoveExe(Runnable removeExe) {
        this.removeExe = removeExe;
    }

    //=======================================//
    @Override
    public void update() {
        float dist = (float) dt.scalar(speed);
        posNow.addAngVec(dist, angle.getValue());
        if (Vec2D.sub(posNow, posEnd).getLength() <= dist) {
            if (removeExe != null) removeExe.run();
            remove();
        }
    }

    @Override
    public void draw(Draw drawer) {
        if (image == null) {
            Vec2D temp = Vec2D.newAngVec(posNow, 10, angle.getValue() - Math.PI);
            drawer.drawLine(posNow, temp, Color.YELLOW, 0.8f);
        } else {
            drawer.drawImage(posNow, image, angle);
        }
    }
}
