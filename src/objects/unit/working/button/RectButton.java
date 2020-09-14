package objects.unit.working.button;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import main.game.gamePanel.listener.Listener;

import java.awt.*;
import java.util.Map;

public class RectButton extends Button {
    private final Vec2D pos;
    private final Vec2D size;

    private Map<ButtonState, Runnable> drawExecute;

    public RectButton(Vec2D pos, Vec2D size) {
        this.pos = new Vec2D(pos);
        this.size = new Vec2D(size);
    }
    public RectButton(Vec2D pos, Vec2D size, Runnable exp) {
        this(pos, size);
        super.setExp(exp);
    }

    public void setPos(Vec2D pos) {
        this.pos.setXY(pos);
    }

    public void setDrawExecute(Map<ButtonState, Runnable> drawExecute) {
        this.drawExecute = drawExecute;
    }

    @Override
    protected void updateState() {
        if (state != ButtonState.PRESSED && Listener.globalMousePos.inRect(pos, pos.clone().add(size))){
            state = ButtonState.FOCUSED;
        } else if (state != ButtonState.PRESSED && !Listener.globalMousePos.inRect(pos, pos.clone().add(size))) {
            state = ButtonState.ACTIVE;
        }
    }

    @Override
    public void update() {
        updateState();
    }

    @Override
    public void draw(GameDrawer drawer) {
        if (drawExecute != null) {
            switch (state) {
                case ACTIVE:  drawExecute.get(ButtonState.ACTIVE).run();  break;
                case FOCUSED: drawExecute.get(ButtonState.FOCUSED).run(); break;
                case PRESSED: drawExecute.get(ButtonState.PRESSED).run(); break;
                case DISABLE: drawExecute.get(ButtonState.DISABLE).run(); break;
            }
        } else {
            switch (state) {
                case ACTIVE:  drawer.fillRect(pos, size, new Color(0xFF000F)); break;
                case FOCUSED: drawer.fillRect(pos, size, new Color(0xFF544A)); break;
                case PRESSED: drawer.fillRect(pos, size, new Color(0xB4FF83)); break;
                default:      drawer.fillRect(pos, size, Color.GRAY);
            }
            drawer.drawRect(pos, size, 3, Color.BLACK);
        }
    }
}
