package objects.unit.working.vehicle.train;

import draw.drawer.GameDrawer;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

public class Platform extends Obj {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.PLATFORM;

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
    public void draw(GameDrawer drawer) {

    }
}
