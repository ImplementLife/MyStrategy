package objects.unit.working.button;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import main.game.gamePanel.listener.Listener;

import java.awt.*;

public class RatioButton extends Button {
    private final Vec2D pos = new Vec2D();
    private float radius;

    public RatioButton(Vec2D pos, float radius, Runnable exp) {
        this.pos.setXY(pos);
        this.radius = radius;
        setExp(exp);
    }

    @Override
    public void setPos(Vec2D pos) {
        this.pos.setXY(pos);
    }

    @Override
    protected void updateState() {
        if (state != ButtonState.PRESSED && Listener.posToMouse(pos).getLength() < radius){
            state = ButtonState.FOCUSED;
        } else if (state != ButtonState.PRESSED && Listener.posToMouse(pos).getLength() > radius) {
            state = ButtonState.ACTIVE;
        }
    }

    @Override
    public void draw(GameDrawer drawer) {
        drawer.fillCircle(pos, (int) (radius + 6), Color.BLACK);
        switch (state) {
            case ACTIVE:  drawer.fillCircle(pos, (int) radius, new Color(0xFF000F)); break;
            case FOCUSED: drawer.fillCircle(pos, (int) radius, new Color(0xFF544A)); break;
            case PRESSED: drawer.fillCircle(pos, (int) radius, new Color(0xB4FF83)); break;
            default:      drawer.fillCircle(pos, (int) radius, Color.GRAY);
        }
    }

}
