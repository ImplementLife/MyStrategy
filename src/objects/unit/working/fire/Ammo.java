package objects.unit.working.fire;

import lib.math.Vec2D;
import objects.unit.working.bullet.Bullet;

public class Ammo {
    public void shot(Vec2D p1, Vec2D p2) {
        new Bullet(p1, p2);
    }
    public void shot(Vec2D p1, Vec2D p2, Runnable exe) {
        new Bullet(p1, p2).setRemoveExe(exe);
    }
}
