package objects.unit.working.bullet;

import draw.drawer.GameDrawer;
import lib.math.Angle;
import lib.math.Vec2D;
import main.game.gamePanel.listener.Listener;
import main.game.gamePanel.listener.events.Analyzer;
import main.game.gamePanel.listener.events.Event;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static update.Updater.dt;

public class Bullet extends Obj {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.BULLET;
    private static HashMap<String, Image> images;
    public static void init() {
        images = new HashMap<>();
        putImage("resource/bullet/trace_10.png");

        analyzer = new Analyzer(e -> {
            if (e.isPressed() && e.getKeyCode() == Event.LEFT_MOUSE_BUTTON) {
                new Bullet(new Vec2D(), Listener.getGlobalMousePos(), null);
            }
        });
    }
    private static void putImage(String path) {
        images.put(path, new ImageIcon(path).getImage());
    }
    //=======================================//
    private static Analyzer analyzer;
    //=======================================//

    private final Vec2D pos;
    private final Vec2D posEnd;
    private final Angle angle;
    private Image image;
    private float speed;
    private Runnable removeExe;

    public void setRemoveExe(Runnable removeExe) {
        this.removeExe = removeExe;
    }

    public Bullet(Vec2D pos, Vec2D posEnd) {
        super(TYPE);
        this.pos = new Vec2D(pos);
        this.posEnd = new Vec2D(posEnd);
        this.angle = new Angle(Vec2D.getAngle(this.pos, this.posEnd));
        this.speed = 1500;
    }

    public Bullet(Vec2D pos, Vec2D posEnd, String name) {
        this(pos, posEnd);
        if (name != null) this.image = images.get(name);
    }

    @Override
    public void update() {
        float dist = (float) dt.scalar(speed);
        pos.addAngVec(dist, angle.getValue());
        if (Vec2D.sub(pos, posEnd).getLength() <= dist) {
            if (removeExe != null) removeExe.run();
            remove();
        }
    }

    @Override
    public void draw(GameDrawer drawer) {
        if (image == null) {
            Vec2D temp = Vec2D.newAngVec(pos, 10, angle.getValue() - Math.PI);
            drawer.drawLine(pos, temp, 1, Color.YELLOW);
        } else {
            drawer.drawImage(pos, image, angle);
        }
    }
}
