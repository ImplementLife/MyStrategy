package game.unit.working.vehicle.train;

import draw.drawer.Draw;
import game.objects.GameObject;
import game.objects.managers.GameObjectTypes;

public class Platform extends GameObject {
    //==========     Static     =============//
    private static final GameObjectTypes TYPE = GameObjectTypes.PLATFORM;

    //=======================================//

    private WayPoint firstWayPoint;
    private WayPoint lastWayPoint;

    public Platform(WayPoint firstWayPoint, WayPoint lastWayPoint) {
        super(TYPE);

        this.firstWayPoint = firstWayPoint;
        this.lastWayPoint = lastWayPoint;

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Draw drawer) {

    }
}
