package update.move.managers;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import objects.unit.working.Unit;
import update.move.Mover;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GroupMoveManager implements Mover {
    private final Vec2D posNow;
    private final Vec2D posEnd;
    private final ArrayList<Unit> units;
    private final Queue<Vec2D> way;
    private final Vec2D wayPoint;
    private final Analyzer analyzer;
    private boolean shift, move;

    public GroupMoveManager() {
        way = new LinkedList<>();
        wayPoint = new Vec2D();
        analyzer = new Analyzer(e -> {
            if (e.isReleased()) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;
            }
            if (e.isPressed()) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
            }
        });
        units = new ArrayList<>();
        posNow = new Vec2D();
        posEnd = new Vec2D();
    }

    private void moveUnitsTo(Vec2D pos) {
        posEnd.setXY(pos);
        for (Unit u : units) {
            u.moveTo(pos/*.addVec(70*Math.random(), Mat.PI2*Math.random())*/);
        }
    }

    @Override
    public void moveTo(Vec2D pos) {
        if (!shift) {
            way.clear();
            moveUnitsTo(pos);
        } else way.offer(pos);
        move = true;
    }

    @Override
    public void update() {
        if (move) {
            int help = 0;
            for (Unit u : units) if (!u.isMove()) help++;
            if (help == units.size()) {
                Vec2D pos = way.poll();
                if (pos != null) {
                    wayPoint.setXY(pos);
                    moveUnitsTo(wayPoint);
                } else if (posNow.sub(posEnd).getLength() < 50) {
                    move = false;
                }
            }
        }
    }

    @Override
    public boolean isMove() {
        return false;
    }

    @Override
    public void draw(GameDrawer drawer) {

    }
}
