package objects.unit.working;

import lib.math.Vec2D;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

public abstract class Unit extends Obj {
    public Unit(ObjTypes type) { super(type); }
    public abstract void moveTo(Vec2D pos);
    public abstract Vec2D getPos();
    public abstract boolean isMove();
    public abstract float getSize();
}
