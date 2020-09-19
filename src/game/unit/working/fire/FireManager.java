package game.unit.working.fire;

import game.unit.working.Unit;

public interface FireManager {
    void update();
    void attack(Unit unit);
    boolean isFire();
    boolean isCanFire();
    int[] getAmmoCount();
}
