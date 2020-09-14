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
//        ArrayList<Obj> objList = new ArrayList<>();
//        while (true) {
//            try {
//                Obj.updateList();
//                objList.addAll(Obj.getObj());
//            } catch (ConcurrentModificationException e) {
//                e.printStackTrace();
//                continue;
//            }
//            //objList.forEach(Obj::draw);
//            for (Obj obj : objList) obj.draw(drawer);
//            break;
//        }
        Obj.updateList();
        for (Obj obj : Obj.getObj()) obj.draw(drawer);

        Angle angle = new Angle(Listener.getMousePos().getAngle());

        drawer.fillTriangle(new Vec2D(), angle, 15, Color.YELLOW);
        drawer.drawTriangle(new Vec2D(), angle, 15, 2, Color.BLACK);
        drawer.fillCircle(new Vec2D(), 15, new Color(0x5ECA6667, true));

        drawer.fillPentagon(new Vec2D(100, 100), new Angle(Math.PI/2), 15, Color.YELLOW);
        drawer.drawPentagon(new Vec2D(100, 100), new Angle(Math.PI/2), 15, 2, Color.BLACK);
        drawer.fillCircle(new Vec2D(100, 100), 15, new Color(0x5ECA6667, true));
    }
}
