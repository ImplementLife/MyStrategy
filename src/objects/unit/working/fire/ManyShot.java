package objects.unit.working.fire;

import lib.Timer;
import objects.unit.working.Unit;

public class ManyShot extends OneShot implements FireManager {
    private int ammoCount[]; //0 - current; 1 - max;
    private Timer reload;

    public ManyShot(Unit parentUnit, int timeReload[], int ammoCount[]) {
        super(parentUnit, timeReload[0], ammoCount[0]);
        this.reload = new Timer(timeReload[1]);
        this.ammoCount = new int[] {ammoCount[1], ammoCount[1]};
    }

    @Override
    protected void fire() {
        super.fire();
        if (isCanFire() && ammoCount[0] > 0 && reload.start()) reload();
    }
}
