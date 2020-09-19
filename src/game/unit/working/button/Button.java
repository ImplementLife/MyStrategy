package game.unit.working.button;

import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import main.game.gamePanel.listener.events.Event;
import game.objects.GameObject;
import game.objects.managers.GameObjectTypes;

import java.awt.event.KeyEvent;
import java.util.Map;

public abstract class Button extends GameObject {
    //==========     Static     =============//
    private static final GameObjectTypes TYPE = GameObjectTypes.BUTTON;

    public enum ButtonState {
        ACTIVE,
        DISABLE,
        PRESSED,
        FOCUSED;
    }

    protected static boolean ctrl;
    //=======================================//
    private Analyzer analyzer;
    private boolean updateState = true;

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

    public void setCanUpdate(boolean updateState) {
        this.updateState = updateState;
    }

    @Override
    public void update() {
        if (updateState) updateState();
    }

    public void setFocus() {
        if (state == ButtonState.FOCUSED) {
            state = ButtonState.PRESSED;
        } else if (!ctrl) {
            state = ButtonState.ACTIVE;
        }
    }

    public ButtonState getState() {
        return state;
    }

    public void setExe(Runnable exe) {
        updateExecute = exe;
    }
}
