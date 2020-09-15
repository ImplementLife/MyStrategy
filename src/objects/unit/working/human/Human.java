package objects.unit.working.human;

import draw.drawer.GameDrawer;
import game.Player;
import lib.math.Angle;
import lib.math.Vec2D;
import objects.game.objects.ObjTypes;
import objects.unit.working.Unit;
import objects.unit.working.fire.ManyShot;
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
        this.setFireManager(new ManyShot(this, new int[] {1200, 5000}, new int[] {5, 10}));
        this.posNow = new Vec2D(pos);
        this.angle = new Angle(0);
        this.type = type;
        this.mover = new MoverHuman(posNow, 300, 16, 1200, 1200);
//        switch (type) {
//            case RIFE: {
//                ammoCountSmall = ammoCountSmallMax = 5;
//                reloadSmall = new Timer(1200);
//                reloadLong = new Timer(5000);
//            } break;
//            case SMG: {
//                ammoCountSmall = ammoCountSmallMax = 30;
//                reloadSmall = new Timer(70);
//                reloadLong = new Timer(5000);
//            } break;
//            case MUNG: {
//                ammoCountSmall = ammoCountSmallMax = 200;
//                reloadSmall = new Timer(40);
//                reloadLong = new Timer(12000);
//            } break;
//            default:
//        }
    }

    //=======================================//

    public HumanType getType() {
        return type;
    }
    public void setType(HumanType type) {
        this.type = type;
    }

    //   Unit Overrides
    private boolean notInjured = true;
    @Override
    public boolean kill() {
        if (!notInjured) {
            remove();
            return true;
        }
        notInjured = false;
        return false;
    }

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
        super.update();
        mover.update();
    }

    @Override
    public void draw(GameDrawer drawer) {
        Color color = Color.GREEN;
        if (Player.isEnemyForCurrent(getPlayer())) color = Color.RED;
        switch (type) {
            case SMG:  drawer.fillCircle(posNow, 10, 4, color); break;
            case RIFE: {
                drawer.fillTriangle(new Vec2D(), angle, 15, color);
                drawer.drawTriangle(new Vec2D(), angle, 15, 2, Color.BLACK);
            } break;
            case MUNG: {
                drawer.fillPentagon(new Vec2D(), angle, 10, color);
                drawer.drawPentagon(new Vec2D(), angle, 10, 2, Color.BLACK);
            } break;
            default:
        }

//        mover.draw();
    }
}
