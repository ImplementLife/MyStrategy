package draw.drawer;

import draw.game.ServiceGameDraw;
import draw.game.camera.Camera;
import lib.math.Angle;
import lib.math.Vec2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameDrawer {
    //==========     Static     =============//

    public static boolean inCamera(Vec2D pos, double size) {
        Camera camera = ServiceGameDraw.getCamera();
        if (pos.getX() < camera.firstPos.getX() - size || pos.getY() < camera.firstPos.getY() - size) return false;
        else return (pos.getX() < camera.endPos.getX() + size) && (pos.getY() < camera.endPos.getY() + size);
    }
    public static Vec2D posOnCamera(Vec2D pos) {
        return Vec2D.sub(pos, ServiceGameDraw.getCamera().firstPos);
    }
    private static boolean rectInCamera(Vec2D posStart, Vec2D size) {
        Vec2D help = size.clone().scalar(0.5);
        double helpLength = help.getLength();
        return inCamera(posStart, helpLength);
    }

    private static class GD extends Drawer {
        private GD(Vec2D size, boolean alpha) {
            super(size, alpha);
        }

        @Override
        protected void draw(Vec2D posStart, Vec2D size, Color color, DrawExp exp) {
            if (rectInCamera(posStart, Vec2D.add(posStart, size))) {
                super.draw(posStart, size, color, exp);
            }
        }

        @Override
        protected void draw(Vec2D posStart, Vec2D size, float angle, Color color, DrawExp exp) {
            if (inCamera(posStart, size.getLength())) {
                super.draw(posStart, size, angle, color, exp);
            }
        }

        @Override
        public void drawImage(Vec2D pos, Vec2D offset, Image image, float angle) {
            if (inCamera(pos, Math.max(offset.getX(), offset.getY()))) {
                super.drawImage(posOnCamera(pos), offset, image, angle);
            }
        }

    }

    //=======================================//
    private GD drawer;

    public GameDrawer(Vec2D size, boolean alpha) {
        this.drawer = new GD(size, alpha);
    }

    public void drawPentagon(Vec2D pos, Angle angle, float size, float thickness, Color color) {
        if (inCamera(pos, size)) drawer.drawPentagon(posOnCamera(pos), angle, size, thickness, color);
    }
    public void fillPentagon(Vec2D pos, Angle angle, float size, Color color) {
        if (inCamera(pos, size)) drawer.fillPentagon(posOnCamera(pos), angle, size, color);
    }

    public void drawTriangle(Vec2D pos, Angle angle, float size, float thickness, Color color) {
        if (inCamera(pos, size)) drawer.drawTriangle(posOnCamera(pos), angle, size, thickness, color);
    }
    public void fillTriangle(Vec2D pos, Angle angle, float size, Color color) {
        if (inCamera(pos, size)) drawer.fillTriangle(posOnCamera(pos), angle, size, color);
    }

    public void fillCircle(Vec2D pos, int radius, Color color) {
        if (inCamera(pos, radius)) drawer.fillCircle(posOnCamera(pos), radius, color);
    }
    public void fillCircle(Vec2D pos, int radius, int border, Color color) {
        if (border > 0) fillCircle(pos, radius + border, Color.BLACK);
        fillCircle(pos, radius, color);
    }

    public void drawLine(Vec2D start, Vec2D end, int thickness, Color color) {
        drawer.drawLine(posOnCamera(start), posOnCamera(end), thickness, color);
    }

    public void drawRect(Vec2D posStart, Vec2D size, Color color) {
        drawer.drawRect(posOnCamera(posStart), size, color);
    }
    public void drawRect(Vec2D posStart, Vec2D size, int thickness, Color color) {
        drawer.drawRect(posOnCamera(posStart), size, thickness, color);
    }
    public void drawRect(Vec2D posStart, Vec2D size, float angle, Color color) {
        drawer.drawRect(posOnCamera(posStart), size, angle, color);
    }

    public void fillRect(Vec2D posStart, Vec2D size, Color color) {
        drawer.fillRect(posOnCamera(posStart), size, color);
    }
    public void fillRect(Vec2D posStart, Vec2D size, float angle, Color color) {
        drawer.fillRect(posOnCamera(posStart), size, angle, color);
    }

    public void drawImage(Vec2D pos, Image image) {
        drawer.drawImage(posOnCamera(pos), image);
    }
    public void drawImage(Vec2D pos, Image image, Angle angle) {
        drawer.drawImage(pos, image, angle.getValue());
    }
    public void drawImage(Vec2D pos, Vec2D offset, Image image, Angle angle) {
        drawer.drawImage(pos, offset, image, angle.getValue());
    }

    public void drawString(Vec2D pos, String text, int size, Color color) {
        drawer.drawString(posOnCamera(pos), text, size, color);
    }
    public void drawString(Vec2D pos, String[] text, int size, Color color) {
        drawer.drawString(posOnCamera(pos), text, size, color);
    }

    //=======================================//

    public BufferedImage getImage() {
        return drawer.getImage();
    }

    public Graphics2D getG() {
        return drawer.getG();
    }

    public void dispose() {
        drawer.dispose();
    }

    public void setSize(Vec2D size) {
        drawer.setSize(size);
    }

    public void setAntialiasing(SettingsG value) {
        drawer.setAntialiasing(value);
    }
    public void setRendering(SettingsG value) {
        drawer.setRendering(value);
    }
    public void setDithering(SettingsG value) {
        drawer.setDithering(value);
    }
    public void setTextAntialiasing(SettingsG value) {
        drawer.setTextAntialiasing(value);
    }
    public void setFractionalMetrics(SettingsG value) {
        drawer.setFractionalMetrics(value);
    }
    public void setAlphaInterpolation(SettingsG value) {
        drawer.setAlphaInterpolation(value);
    }
    public void setColorRendering(SettingsG value) {
        drawer.setColorRendering(value);
    }

    public void setInterpolation(Object value) {
        drawer.setInterpolation(value);
    }
    public void setResolutionVariant(Object value) {
        drawer.setResolutionVariant(value);
    }
    public void setStrokeControl(Object value) {
        drawer.setStrokeControl(value);
    }

    public void setAll(SettingsDrawer settingsDrawer) {
        drawer.setAll(settingsDrawer);
    }
}
