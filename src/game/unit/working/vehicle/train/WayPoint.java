package game.unit.working.vehicle.train;

import lib.math.Vec2D;

import java.util.Objects;

public class WayPoint {
    WayPoint previousWayPoint;
    WayPoint nextWayPoint;

    private boolean deadWay = true;

    public Vec2D getPos() {
        return pos;
    }

    private Vec2D pos;

    public WayPoint(Vec2D pos) {
        RailWay.allWayPoint.add(this);
        this.pos = pos;
    }

    public boolean isDeadWay() {
        return deadWay;
    }
    private void setDeadWay() {
        deadWay = previousWayPoint == null || nextWayPoint == null;
    }

    public void setNextWayPoint(WayPoint nextWayPoint) {
        this.nextWayPoint = nextWayPoint;
        setDeadWay();
    }
    public void setPreviousWayPoint(WayPoint previousWayPoint) {
        this.previousWayPoint = previousWayPoint;
        setDeadWay();
    }

    public WayPoint getNext(WayPoint wayPoint) {
        if (nextWayPoint.equals(wayPoint)) return previousWayPoint;
        if (previousWayPoint.equals(wayPoint)) return nextWayPoint;
        return null;
    }

    public WayPoint getNextWayPoint() {
        return nextWayPoint;
    }
    public WayPoint getPreviousWayPoint() {
        return previousWayPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayPoint wayPoint = (WayPoint) o;
        return deadWay == wayPoint.deadWay &&
                Objects.equals(previousWayPoint, wayPoint.previousWayPoint) &&
                Objects.equals(nextWayPoint, wayPoint.nextWayPoint) &&
                Objects.equals(pos, wayPoint.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousWayPoint, nextWayPoint, deadWay, pos);
    }
}
