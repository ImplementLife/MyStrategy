package objects.unit.working.human;

import draw.drawer.GameDrawer;
import lib.math.Angle;
import lib.math.Vec2D;
import objects.game.objects.ObjTypes;
import objects.unit.working.Unit;
import update.move.Mover;

import java.awt.*;

public class Human extends Unit {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.HUMAN;

    //=======================================//
    private final Vec2D posNow;
    private final Angle angle;
    private HumanType type;

    private final Mover mover;

    //=======================================//

    public Human(Vec2D pos) {
        this(pos, HumanType.SMG);
    }

    public Human(Vec2D pos, HumanType type) {
        super(TYPE);
        this.posNow = new Vec2D(pos);
        this.angle = new Angle(0);
        this.type = type;
        this.mover = new MoverHuman(posNow, 300, 16, 1200, 1200);
    }

    //=======================================//

    public HumanType getType() {
        return type;
    }
    public void setType(HumanType type) {
        this.type = type;
    }

    //   Unit Overrides

    @Override
    public boolean isMove() {
        return mover.isMove();
    }

    @Override
    public void moveTo(Vec2D pos) {
        mover.moveTo(pos);
    }

    @Override
    public Vec2D getPos() {
        return posNow;
    }

    @Override
    public float getSize() {
        return 0;
    }

    //=======================================//
    //   Obj Overrides

    @Override
    public void update() {
        mover.update();
    }

    @Override
    public void draw(GameDrawer drawer) {
        Color color = Color.GREEN;
        switch (type) {
            case SMG:  drawer.fillCircle(posNow, 10, 4, color); break;
            case RIFE: drawer.fillTriangle(posNow, angle, 15, color); break;
            case MUNG: break;
            default:
        }

//        mover.draw();
    }
}
