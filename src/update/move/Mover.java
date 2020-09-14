package update.move;

import draw.drawer.GameDrawer;
import lib.math.Vec2D;

public interface Mover {
    void moveTo(Vec2D pos);
    void update();
    boolean isMove();

    @Deprecated
    void draw(GameDrawer drawer);
}
