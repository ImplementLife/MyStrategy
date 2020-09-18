package objects.unit.working.vehicle.tank;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import objects.game.objects.ObjTypes;
import objects.unit.working.Unit;
import objects.unit.working.vehicle.DBT;
import objects.unit.working.vehicle.Element;
import update.move.Mover;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Tank extends Unit {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.TANK;

    private static Map<String, Image> images;
    static {
        images = new HashMap<>();
        putImage("resource/entity/equipment/Tank/default/ally/body.png");
        putImage("resource/entity/equipment/Tank/default/ally/turret.png");
    }
    private static void putImage(String path) {
        images.put(path, new ImageIcon(path).getImage());
    }
    //=======================================//

    private final Vec2D posNow;
    private final Vec2D posEnd;

    private final Vec2D posTurretNow;
    private double lTurret = 10;

    private final Element body;
    private final Element turret;
    private final DBT dbt;
    private final Mover mover;

    public Tank(Vec2D pos, float angle) {
        super(TYPE);
        this.posNow = new Vec2D(pos);
        this.posEnd = new Vec2D(pos);
        this.posTurretNow = new Vec2D(posNow).addAngVec(lTurret, angle);

        this.dbt = new DBT(angle, 1.2f, 1.5f);
        this.mover = new MoverTank(dbt, posNow, 300, 120, 200, 600);

        this.body = new Element(posNow, images.get("resource/entity/equipment/Tank/default/ally/body.png"), angle);
        this.turret = new Element(posTurretNow, images.get("resource/entity/equipment/Tank/default/ally/turret.png"), angle);
    }

    public void rotateBody(Vec2D pos) {
        dbt.rotateBody((float) Vec2D.getAngle(pos, posNow));
    }
    public void rotateTurret(Vec2D pos) {
        dbt.rotateTurret((float) Vec2D.getAngle(pos, posTurretNow));
    }

    //=======================================//   Unit Overrides
    @Override
    public void attack(Unit unit) {

    }

    @Override
    public boolean kill() {
        return false;
    }

    @Override
    public Vec2D getPos() {
        return posNow;
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
    public float getSize() {
        return 0;
    }

    //=======================================//   Obj Overrides
    @Override
    public void update() {
        super.update();
        mover.update();
        posTurretNow.setAngVec(posNow, lTurret, dbt.getAngleBody());
        body.setAngle(dbt.getAngleBody());
        turret.setAngle(dbt.getAngleTurret());
    }

    @Override
    public void draw(GameDrawer drawer) {
        body.draw(drawer);
        turret.draw(drawer);
//        mover.draw();
    }
}
