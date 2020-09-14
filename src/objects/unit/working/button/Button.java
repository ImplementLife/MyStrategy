package objects.unit.working.button;

import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import main.game.gamePanel.listener.events.Event;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

import java.awt.event.KeyEvent;
import java.util.Map;

public abstract class Button extends Obj {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.BUTTON;

    protected static boolean ctrl;
    //=======================================//
    private Analyzer analyzer;

    protected ButtonState state = ButtonState.ACTIVE;
    private Runnable updateExecute;

    public void setStateExe(Map<ButtonState, Runnable> stateExe) {
        this.stateExe = stateExe;
    }

    private Map<ButtonState, Runnable> stateExe;

    public abstract void setPos(Vec2D pos);

    public Button() {
        super(TYPE);
        analyzer = new Analyzer((e -> {
            if (e.isReleased()) {
                if (e.getKeyCode() == Event.LEFT_MOUSE_BUTTON) setFocus();
                if (e.getKeyCode() == Event.RIGHT_MOUSE_BUTTON && this.state == ButtonState.PRESSED) {
                    if (updateExecute != null) updateExecute.run();
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = false;
            }
            if (e.isPressed()) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = true;
            }
        }));
    }

    protected abstract void updateState();

    public void update() {
        updateState();
    }

    public void setFocus() {
        if (state == ButtonState.FOCUSED) {
            state = ButtonState.PRESSED;
        } else if (!ctrl) {
            state = ButtonState.ACTIVE;
        }
    }

    public void setExp(Runnable exp) {
        updateExecute = exp;
    }
}
