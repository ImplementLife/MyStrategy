package game.update.move.managers;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import game.unit.working.Unit;
import game.update.move.Mover;
import game.update.move.WayPoint;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

public class MoveManager implements Mover {
    private final Queue<WayPoint> way;
    private final Analyzer analyzer;
    private boolean shift, move;

    private final Unit unit;
    private final Vec2D posNow;
    private final Vec2D posEnd;

    public MoveManager(Unit unit) {
        this.unit = unit;
        way = new LinkedList<>();
        analyzer = new Analyzer(e -> {
            if (e.isReleased()) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;
            }
            if (e.isPressed()) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
            }
        });
        posNow = unit.getPos();
        posEnd = new Vec2D();
    }

    @Override
    public void moveTo(Vec2D pos) {
//        if (!shift) {
//            way.clear();
//            unit.moveTo(new WayPoint(pos));
//        } else way.offer(new WayPoint(pos));
//        move = true;
    }

    @Override
    public boolean isMove() {
        return false;
    }

    @Override
    public void update() {
//        if (move) {
//            if (!unit.isMove()) {
//                WayPoint point = way.poll();
//                if (point != null) unit.moveTo(point);
//                else move = unit.isMove();
//            }
//        }
    }

    @Override
    public void draw(GameDrawer drawer) {

    }
}
