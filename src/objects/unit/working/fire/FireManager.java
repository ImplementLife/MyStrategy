package objects.unit.working.fire;

import objects.unit.working.Unit;

public interface FireManager {
    void update();
    void attack(Unit unit);
    boolean isFire();
    boolean isCanFire();
}
