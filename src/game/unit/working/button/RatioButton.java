package game.unit.working.button;

import draw.drawer.Draw;
import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import main.game.gamePanel.listener.Listener;

import java.awt.*;

public class RatioButton extends Button {
    private final Vec2D pos = new Vec2D();
    private float radius;

    public RatioButton(Vec2D pos, float radius, Runnable exe) {
        this.pos.setXY(pos);
        this.radius = radius;
        setExe(exe);
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
    public void draw(Draw drawer) {
        float border = 3;
        Color color = Color.GRAY;
        switch (state) {
            case ACTIVE:  color = new Color(0xFF000F); break;
            case FOCUSED: color = new Color(0xFF544A); break;
            case PRESSED: color = new Color(0xB4FF83); break;
        }
        drawer.fillCircle(pos, radius, color, Color.BLACK, border);
    }

}
