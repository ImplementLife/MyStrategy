package game.unit.working.fire;

import java.util.LinkedList;
import java.util.Queue;

public class Clip {
    private Queue<Ammo> ammo;
    public Clip(int ammoCount) {
        this.ammo = new LinkedList<>();
        for (int i = 0; i < ammoCount; i++) ammo.offer(new Ammo());
    }
    public Ammo getAmmo() {
        return ammo.poll();
    }
    public int getAmmoCount() {
        return ammo.size();
    }
}
