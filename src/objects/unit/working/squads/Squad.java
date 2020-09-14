package objects.unit.working.squads;

import draw.drawer.GameDrawer;
import lib.math.Angle;
import lib.math.Vec2D;
import main.game.gamePanel.listener.Listener;
import main.game.gamePanel.listener.events.Analyzer;
import objects.FX.Arrow;
import objects.game.objects.Id;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;
import objects.unit.working.Unit;
import objects.unit.working.button.Button;
import objects.unit.working.button.RatioButton;
import update.move.Mover;
import update.move.managers.MoveManager;

import java.awt.event.KeyEvent;
import java.util.*;

public class Squad extends Unit {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.SQUAD;

    //=======================================//
    private final Vec2D centerPos = new Vec2D();
    private final Vec2D endPos = new Vec2D();
    private boolean move;
    private final Mover mover;

    private Map<Id, Unit> groupMembers;
    private final Vec2D sizeButton;

    private final Vec2D posForButton;

    private SquadLevels level;
    private SquadTypes type;

    private final Button button;
    private final Arrow arrow = new Arrow();

    private final Queue<Vec2D> way;
    private final Vec2D wayPoint;
    private boolean shift;
    private final Analyzer analyzer;

    private float minSize, maxSize;

    public Squad() {
        super(TYPE);
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
        groupMembers = new HashMap<>();
        sizeButton = new Vec2D(50, 25);

        posForButton = Vec2D.sub(getPos(), Vec2D.scalar(sizeButton,0.5));
//        button = new RectButton(posForButton, sizeButton, () -> moveTo(Listener.getMousePos()));
        button = new RatioButton(posForButton, 20, () -> moveTo(Listener.getMousePos()));
        mover = new MoveManager(this);

        this.minSize = 75;
        this.maxSize = 150;
    }

    public void updateButtonPos() {
        posForButton.setXY(getPos()).sub(Vec2D.scalar(sizeButton,0.5));
        button.setPos(posForButton);
    }

    public void putMembers(Id id) {
        putMembers((Unit) Obj.getObjFromId(id));
    }
    public void putMembers(Unit unit) {
        if (unit != this) groupMembers.put(unit.getId(), unit);
    }
    private void moveUnitsTo(Vec2D pos) {
        endPos.setXY(pos);
        for (Unit u : groupMembers.values()) {
            u.moveTo(Vec2D.newAngVec(pos, minSize + (maxSize - minSize) * Math.random(), Angle.E * Math.random()));
        }
    }

    //=======================================//
    //   Unit Overrides

    @Override
    public boolean isMove() {
        int help = 0;
        Collection<Unit> gm = groupMembers.values();
        for (Unit u : gm) if (!u.isMove()) help++;
        return help == gm.size();
    }

    @Override
    public void moveTo(Vec2D pos) {
        mover.moveTo(pos);
        if (!shift) {
            way.clear();
            moveUnitsTo(pos);
        } else way.offer(pos);
        move = true;
        arrow.setDraw(true);
    }

    @Override
    public Vec2D getPos() {
        List<Vec2D> pos = new ArrayList<>();
        for (Unit u : groupMembers.values()) pos.add(u.getPos());
        return centerPos.setXY(Vec2D.midVec(pos));
    }

    @Override
    public float getSize() {
        return 0;
    }

    //=======================================//
    //   Obj Overrides

    @Override
    public void update() {
        if (move) {
            centerPos.setXY(getPos());
            posForButton.setXY(centerPos).sub(Vec2D.scalar(sizeButton,0.5));
            button.setPos(posForButton);

            if (isMove()) {
                Vec2D pos = way.poll();
                if (pos != null) {
                    wayPoint.setXY(pos);
                    moveUnitsTo(wayPoint);
                } else if (centerPos.sub(endPos).getLength() < 50) {
                    arrow.setDraw(false);
                    move = false;
                }
            }

            arrow.setFirst(getPos());
            arrow.setEnd(endPos);
        }
    }

    @Override
    public void draw(GameDrawer drawer) {
        arrow.draw(drawer);
    }
}
