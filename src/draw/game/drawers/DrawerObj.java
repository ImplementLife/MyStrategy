package draw.game.drawers;

import draw.drawer.GameDrawer;
import lib.math.Angle;
import lib.math.Vec2D;
import main.game.gamePanel.listener.Listener;
import objects.game.objects.Obj;

import java.awt.*;

public class DrawerObj implements Drawer {
    private GameDrawer drawer;

    public DrawerObj(GameDrawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void draw() {
        Obj.updateList();
        try { for (Obj obj : Obj.getObj()) obj.draw(drawer); }
        catch (Exception e) { e.printStackTrace(); }
    }
}
