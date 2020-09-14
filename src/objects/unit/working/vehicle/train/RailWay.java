package objects.unit.working.vehicle.train;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

import java.awt.*;
import java.util.ArrayList;

public class RailWay extends Obj {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.RAIL_WAY;


    //=======================================//

    public static ArrayList<WayPoint> allWayPoint = new ArrayList<>();
    public static ArrayList<Intersection> allIntersection = new ArrayList<>();
    public static ArrayList<RailWay> allRailWay = new ArrayList<>();
    static {
        //Создаём железную дорогу
//        new WayPoint(new Vec2D(-200, 200));
//        createNewWayPoint(new Vec2D(800, 200), allWayPoint.get(0));
//        new RailWay(allWayPoint.get(0), allWayPoint.get(1));
//        createNewWayPoint(new Vec2D(1400, 400), allWayPoint.get(1));
//        new RailWay(allWayPoint.get(1), allWayPoint.get(2));
//        createNewWayPoint(new Vec2D(2000, 600), allWayPoint.get(2));
//        new RailWay(allWayPoint.get(2), allWayPoint.get(3));
//        createNewWayPoint(new Vec2D(2200, 1600), allWayPoint.get(3));
//        new RailWay(allWayPoint.get(3), allWayPoint.get(4));
//        createNewWayPoint(new Vec2D(1400, 2600), allWayPoint.get(4));
//        new RailWay(allWayPoint.get(4), allWayPoint.get(5));
//        createNewWayPoint(new Vec2D(0, 2000), allWayPoint.get(5));
//        new RailWay(allWayPoint.get(5), allWayPoint.get(6));
//        createNewWayPoint(new Vec2D(-800, 800), allWayPoint.get(6));
//
//        allWayPoint.get(7).setNextWayPoint(allWayPoint.get(0));
//        allWayPoint.get(0).setPreviousWayPoint(allWayPoint.get(7));
//
//        new RailWay(allWayPoint.get(6), allWayPoint.get(7));
//        new RailWay(allWayPoint.get(7), allWayPoint.get(0));


//        new WayPoint(new Vec2D(0, 200));
//        createNewWayPoint(new Vec2D(2000*2, 200), allWayPoint.get(0));
//        new RailWay(allWayPoint.get(0), allWayPoint.get(1));
//        createNewWayPoint(new Vec2D(2400*2, 400), allWayPoint.get(1));
//        new RailWay(allWayPoint.get(1), allWayPoint.get(2));
//        createNewWayPoint(new Vec2D(2600*2, 600), allWayPoint.get(2));
//        new RailWay(allWayPoint.get(2), allWayPoint.get(3));
//        createNewWayPoint(new Vec2D(2600*2, 1000), allWayPoint.get(3));
//        new RailWay(allWayPoint.get(3), allWayPoint.get(4));
//        createNewWayPoint(new Vec2D(2400*2, 1200), allWayPoint.get(4));
//        new RailWay(allWayPoint.get(4), allWayPoint.get(5));
//        createNewWayPoint(new Vec2D(0, 800), allWayPoint.get(5));
//        new RailWay(allWayPoint.get(5), allWayPoint.get(6));
//        createNewWayPoint(new Vec2D(-400*2, 200), allWayPoint.get(6));
//
//        allWayPoint.get(7).setNextWayPoint(allWayPoint.get(0));
//        allWayPoint.get(0).setPreviousWayPoint(allWayPoint.get(7));
//
//        new RailWay(allWayPoint.get(6), allWayPoint.get(7));
//        new RailWay(allWayPoint.get(7), allWayPoint.get(0));

        RailWay firstRailWay = new RailWay(new Vec2D(0, 200), new Vec2D(10000, 200));
        allRailWay.get(0).addNew(new Vec2D(10800, 300));
        allRailWay.get(1).addNew(new Vec2D(11800, 1700));
        allRailWay.get(2).addNew(new Vec2D(10000, 2800));
        allRailWay.get(3).addNew(new Vec2D(0,2800));
        allRailWay.get(4).addNew(new Vec2D(-800, 2000));
        allRailWay.get(5).addNew(new Vec2D(-800, 1000));
        allRailWay.get(6).link(firstRailWay);

    }

    public static WayPoint createNewWayPoint(Vec2D pos, WayPoint wayPoint) {
        WayPoint newWayPoint = new WayPoint(pos);

        wayPoint.nextWayPoint = newWayPoint;
        newWayPoint.previousWayPoint = wayPoint;
        return newWayPoint;
    }
    public static void createNewIntersection(RailWay mainWayPoint, RailWay leftWayPoint, RailWay rightWayPoint) {
        new Intersection(mainWayPoint, leftWayPoint, rightWayPoint);
    }
    public static class Intersection {
        private RailWay mainWayPoint;
        private RailWay leftWayPoint;
        private RailWay rightWayPoint;

        public Intersection(RailWay mainWayPoint, RailWay leftWayPoint, RailWay rightWayPoint) {
            this.mainWayPoint = mainWayPoint;
            this.leftWayPoint = leftWayPoint;
            this.rightWayPoint = rightWayPoint;
            allIntersection.add(this);
        }
    }

    //=======================================//

    private WayPoint left;
    private WayPoint right;

//    public RailWay(Vec2D left, Vec2D right) {
//        super(TYPE);
//
//        allRailWay.add(this);
//        this.left = new WayPoint(left);
//        this.right = new WayPoint(right);
//    }
    public RailWay(WayPoint left, WayPoint right) {
        super(TYPE);

        allRailWay.add(this);
        this.left = left;
        this.right = right;
    }
    private RailWay(Vec2D start, Vec2D end) {
        super(TYPE);

        allRailWay.add(this);

        this.left = new WayPoint(start);
        this.right = createNewWayPoint(end, this.left);
    }


    public WayPoint getLeft() {
        return left;
    }
    public WayPoint getRight() {
        return right;
    }

    public RailWay addNew(Vec2D pos) {
        return new RailWay(right, createNewWayPoint(pos, this.right));
    }
    public RailWay link(RailWay railWay) {
        this.right.nextWayPoint = railWay.left;
        railWay.left.previousWayPoint = this.right;
        return new RailWay(this.right, railWay.left);
    }

    @Override
    public void update() {

    }
    @Override
    public void draw(GameDrawer drawer) {
        drawer.drawLine(left.getPos(), right.getPos(), 20, Color.BLACK);
        drawer.drawLine(left.getPos(), right.getPos(), 15, Color.GRAY);
    }
}
