package game.unit.working.human;

import draw.drawer.Draw;
import game.Player;
import lib.math.Angle;
import lib.math.Vec2D;
import game.objects.managers.GameObjectTypes;
import game.unit.working.Unit;
import game.unit.working.fire.FireManagerImpl;
import game.update.move.Mover;

import java.awt.*;

public class Human extends Unit {
    //==========     Static     =============//
    private static final GameObjectTypes TYPE = GameObjectTypes.HUMAN;

    public enum Type {
        SMG(5),
        RIFE(12),
        MUNG(10);
        int size;

        Type(int size) {
            this.size = size;
        }
    }
    //=======================================//
    private final Vec2D posNow;
    private final Angle angle;

    private final Mover mover;
    private Type type;

    //=======================================//

    public Human(Vec2D pos) {
        this(pos, Type.SMG);
    }

    public Human(Vec2D pos, Type type) {
        super(TYPE);
        this.setFireManager(new FireManagerImpl(this,80, 2000, 1000, 10));
        this.posNow = new Vec2D(pos);
        this.angle = new Angle(0);
        this.mover = new MoverHuman(posNow, 300, 16, 1200, 1200);

        double random = Math.random();
        if (random < 0.33) this.type = Type.MUNG;
        else if (random > 0.66) this.type = Type.RIFE;
        else this.type = type;
    }

    //=======================================//

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    //   Unit Overrides
    private float health = 10_000;
    @Override
    public boolean kill() {
        health--;
        if (health <=0 ) {
            remove();
            return true;
        }
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
        return type.size;
    }

    //=======================================//
    //   Obj Overrides

    @Override
    public void update() {
        super.update();
        mover.update();
    }

    @Override
    public void draw(Draw drawer) {
        angle.setValue(angle.getValue() + 0.1f);
        Color color = Color.GREEN;
        if (Player.isEnemyForCurrent(getPlayer())) color = Color.RED;
        switch (type) {
            case SMG:  drawer.fillShape(posNow, angle, color, Color.BLACK, type.size, 4, 2); break;
            case RIFE: drawer.fillShape(posNow, angle, color, Color.BLACK, type.size, 3, 2); break;
            case MUNG: drawer.fillShape(posNow, angle, color, Color.BLACK, type.size, 5, 2); break;
        }
        //draw ammo count
//        {
//            int[] i = fireManager.getAmmoCount();
//            drawer.drawString(posNow.clone().add(6, 6), i[0] + "/" + i[1], 16, Color.YELLOW);
//        }
//
//        //draw health
//        {
//            Vec2D vecHealth = posNow.clone().sub(25, 20);
//            drawer.drawLine(vecHealth, vecHealth.clone().addX(50), 2, Color.GRAY);
//            drawer.drawLine(vecHealth, vecHealth.clone().addX(health/200), 2, Color.WHITE);
//            drawer.drawRect(vecHealth.sub(2,2), new Vec2D(54, 4), Color.BLACK, 2);
//        }

//        mover.draw();
    }


}
