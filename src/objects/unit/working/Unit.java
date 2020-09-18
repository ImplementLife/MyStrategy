package objects.unit.working;

import game.Player;
import lib.math.Vec2D;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;
import objects.unit.working.fire.FireManager;

import java.util.HashSet;

public abstract class Unit extends Obj {
    //==========     Static     =============//
    protected static HashSet<Unit> units = new HashSet<>();

    //================================//
    private final Player player = new Player(0);
    protected FireManager fireManager;

    //================================//
    public Unit(ObjTypes type, FireManager fireManager, int player) {
        this(type, fireManager);
        this.player.setPlayer(player);
    }
    public Unit(ObjTypes type, FireManager fireManager) {
        this(type);
        this.fireManager = fireManager;
    }
    public Unit(ObjTypes type) {
        super(type);
        units.add(this);
    }

    //================================//
    public abstract void moveTo(Vec2D pos);
    public abstract boolean isMove();
    public abstract Vec2D getPos();
    public abstract float getSize();

    //================================//   Work with the players
    public void setPlayer(Player player) {
        this.player.setPlayer(player);
    }
    public void setPlayer(int player) {
        this.player.setPlayer(player);
    }
    public Player getPlayer() {
        return player;
    }

    public boolean isEnemy(Player p) {
        return player.isEnemy(p);
    }
    public boolean isEnemy(Unit u) {
        return player.isEnemy(u.getPlayer());
    }
    public boolean isEnemy() {
        return player.isEnemy();
    }

    //================================//
    @Override
    public void remove() {
        super.remove();
        units.remove(this);
    }

    @Override
    public void update() {
        if (fireManager != null) fireManager.update();
    }

    public abstract boolean kill();

    public void attack(Unit unit) {
        if (fireManager != null) fireManager.attack(unit);
    }

    public boolean isFire() {
        return fireManager != null && fireManager.isFire();
    }
    public boolean isCanFire() {
        return fireManager != null && fireManager.isCanFire();
    }

    //================================//
    public void setFireManager(FireManager fireManager) {
        this.fireManager = fireManager;
    }
}
