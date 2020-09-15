package objects.unit.working.fire;

import lib.Timer;
import objects.unit.working.Unit;
import objects.unit.working.bullet.Bullet;

public abstract class OneShot implements FireManager {
    private final Unit parentUnit;
    private Unit attackUnit;

    private int ammoCount[]; //0 - current; 1 - max;
    private Timer reload;
    private boolean fire;

    public OneShot(Unit parentUnit, int timeReload, int ammoCount) {
        this.parentUnit = parentUnit;
        this.reload = new Timer(timeReload);
        this.ammoCount = new int[] {ammoCount, ammoCount};
    }

    protected final void reload() {
        ammoCount[0] = ammoCount[1];
    }

    protected void fire() {
        if (ammoCount[0] > 0 && attackUnit != null && reload.start()) {
            new Bullet(parentUnit.getPos(), attackUnit.getPos()).setRemoveExe(() -> {
                if (Math.random() > 0.3) {
                    if (attackUnit != null) {
                        attackUnit.kill();
                    } else {
                        fire = false;
                    }
                }
            });
            ammoCount[0]--;
        }
    }

    @Override
    public boolean isCanFire() {
        return ammoCount[0] > 0;
    }

    @Override
    public boolean isFire() {
        return fire;
    }

    @Override
    public final void attack(Unit unit) {
        if (unit != null && !unit.isRemoved()) {
            if (parentUnit.isEnemy(unit)) {
                this.attackUnit = unit;
                fire = true;
            }
        }
    }

    @Override
    public final void update() {
        if (fire) {
            if (!attackUnit.isRemoved()) {
                fire();
            } else {
                attackUnit = null;
                fire = false;
            }
        }
    }
}
