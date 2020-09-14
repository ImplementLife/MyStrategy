package objects.unit.working.vehicle.train;

import draw.drawer.GameDrawer;
import draw.game.ServiceGameDraw;
import lib.math.Vec2D;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;
import objects.unit.working.button.RectButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Wagon extends Obj {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.WAGON;

    private static Image img;
    static {
        img = new ImageIcon("resource/equipment/railEquipment/wagon/wagon.png").getImage();
    }

    //=======================================//
    private RectButton button;

    private Vec2D frontTrolley;
    private Vec2D backTrolley;

    private WayPoint backWayPoint;
    private WayPoint frontWayPoint;

    private WayPoint nextWayPoint;

    public boolean isDirectionForward() {
        return directionForward;
    }
    public void setDirectionForward(boolean directionForward) {
        this.directionForward = directionForward;
    }

    private boolean directionForward = true;
    //Направление движения вперёд или назад
    private boolean move = true;

    private Vec2D posNow;
    private final float LENGTH = 400;
    private final float MAX_SPEED = 10f;
    private float speed = 0f;
    private float angleNow;

    private ArrayList<WayPoint> way;
    public void setWay(ArrayList<WayPoint> way) {
        this.way = new ArrayList<>(way);
    }

    public Wagon(RailWay railWay, double lengthOfBack) {
        super(TYPE);
        this.frontWayPoint = railWay.getRight();
        this.backWayPoint = railWay.getLeft();

        Vec2D temp = Vec2D.sub(backWayPoint.getPos(), frontWayPoint.getPos());
        angleNow = (float) temp.getAngle();
        this.posNow = Vec2D.newAngVec(backWayPoint.getPos(), lengthOfBack, angleNow);

        frontTrolley = Vec2D.newAngVec(posNow, LENGTH/2, angleNow);
        backTrolley = Vec2D.newAngVec(posNow, LENGTH/2, angleNow - Math.PI);

        nextWayPoint = frontWayPoint;


        button = new RectButton(posNow, new Vec2D(100, 100), () -> ServiceGameDraw.getCamera().tracking(Vec2D.newAngVec(new Vec2D(), speed, angleNow)));
    }

    private void move() {
        if (speed < MAX_SPEED) speed += 0.5;
        if (move) {
            if (directionForward) {
                Vec2D backForNext = Vec2D.sub(backTrolley, nextWayPoint.getPos());
                double tempAngle = backForNext.getAngle();
                if (backForNext.getLength() < speed) {
                    tempAngle = Vec2D.getAngle(nextWayPoint.getPos(), nextWayPoint.getNextWayPoint().getPos());
                    backTrolley.setXY(Vec2D.newAngVec(nextWayPoint.getPos(), speed - backForNext.getLength(), tempAngle));
                    nextWayPoint = nextWayPoint.getNextWayPoint();
                } else {
                    backTrolley.addAngVec(speed, tempAngle);
                }
                backForNext = Vec2D.sub(backTrolley, nextWayPoint.getPos());

                if (backForNext.getLength() < LENGTH) {
                    double newTempAngle = Vec2D.scalarProd(
                            backForNext,
                            Vec2D.sub(nextWayPoint.getNextWayPoint().getPos(), nextWayPoint.getPos()));
                    Vec2D temp = help(nextWayPoint.getNextWayPoint().getPos(), backForNext.getLength(), newTempAngle);
                    frontTrolley.setXY(temp);
                } else {
                    frontTrolley.addAngVec(speed, tempAngle);
                    //System.out.println();
                }
                angleNow = (float) Vec2D.getAngle(backTrolley, frontTrolley);
                posNow.setXY(Vec2D.newAngVec(backTrolley, LENGTH/2, angleNow));
                //Camera.tracking(new Vec2D().addVec(speed, tempAngle));
            } else {
//                Vec2D frontForNext = Vec2D.sub(frontTrolley.getPos(), nextWayPoint.getPos());
//                frontTrolley.addPos(speed, (float) (Vec2D.getAngle(frontForNext) - Math.PI));
//                frontForNext = Vec2D.sub(frontTrolley.getPos(), nextWayPoint.getPos());
//                if (frontForNext.getLength() < LENGTH) {
//                    //if (frontForNext.getLength() < speed) nextWayPoint = nextWayPoint.getNextWayPoint();
//                    Vec2D dist = nextWayPoint.getNextWayPoint().getPos();
//                    Vec2D temp = help(dist, frontForNext.getLength(), 15);
//                    backTrolley.setPos(temp);
//                } else backTrolley.addPos(speed, (float) (angleNow - Math.PI));
//                angleNow = (float) Vec2D.getAngle(backTrolley.getPos(), frontTrolley.getPos());
//                posNow.setXY(Vec2D.addVec(backTrolley.getPos(), LENGTH/2, (float) (angleNow - Math.PI)));
            }
        }
    }

    private Vec2D help(Vec2D dir, double length, double angle) {
        double length2 = getLength(angle, LENGTH, length);
        return Vec2D.newAngVec(nextWayPoint.getPos(), length2, Vec2D.getAngle(nextWayPoint.getPos(), dir));
    }
    private static double getLength(double ang, double length, double dir) {
        return length * (Math.sin(Math.PI - ang - Math.asin((dir / length) * Math.sin(ang))) / Math.sin(ang));
    }

    @Override
    public void update() {
        move();
        button.setPos(posNow);
    }
    @Override
    public void draw(GameDrawer drawer) {
        //drawer.drawCircle(nextWayPoint.getPos(), 100, 5, Color.GREEN);

        drawer.drawImage(posNow, img, angleNow);
        drawer.drawRect(posNow, new Vec2D(200, 400), angleNow, Color.RED);
        //drawer.drawCircle(backTrolley, 50, 2, Color.RED);
        //drawer.drawCircle(frontTrolley, 50, 2, Color.BLUE);
    }

//    private static class Searcher {
//        private ArrayList<LinkedList<Id>> ways = new ArrayList<>();
//        private LinkedList<Id> way;
//
//        //========================================//
//
//        private void createWays(Id baseNow, LinkedList<Id> way) {
//            LinkedList<Id> tempWay = new LinkedList<>(way);
//            tempWay.add(baseNow);
//            if (baseNow.equals(targetBase)) {
//                ways.add(tempWay);
//                return;
//            }
//
//            for (Id link : ((Base) getObjFromId(baseNow)).getLinkBases()) {
//                if (!tempWay.contains(link)) {
//                    createWays(link, tempWay);
//                }
//            }
//        }
//
//        //========================================//
//
//        public void selectWay() {
//            createWays(baseNow, new LinkedList<Id>());
//            //Выбор случайного пути
//            {
//                int countBases = ways.size() - 1;
//                way = ways.get((int) Math.round(countBases * Math.random()));
//                ways.clear();
//            }
//        }
//        public void goToNextBase() {
//            if (!move) {
//                if (!way.isEmpty()) {
//                    //if (!way.isEmpty()) {
//                    nextBase = way.removeFirst();
//                    posEnd = new Vector2D(((Base) getObjFromId(nextBase)).getPosForUnit());
//                    move = true;
//                    //}
//                } else {
//                    selectWay();
//                }
//            }
//        }
//
//    }

}
