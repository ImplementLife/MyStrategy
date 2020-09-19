package draw.drawer;

import game.draw.ServiceGameDraw;
import game.draw.camera.Camera;
import lib.math.Angle;
import lib.math.Vec2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class GameDrawer implements Draw {
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

    //=======================================//
    private Drawer drawer;
    public GameDrawer(Vec2D size, boolean alpha) {
        this.drawer = new Drawer(size, alpha);
    }

    //=======================================//

    @Override
    public void drawString(Vec2D pos, String text, int size, Color color) {
        drawer.drawString(posOnCamera(pos), text, size, color);
    }

    @Override
    public void drawString(Vec2D pos, String[] text, int size, Color color) {
        drawer.drawString(posOnCamera(pos), text, size, color);
    }

    @Override
    public void drawShape(Vec2D pos, Angle angle, Color color, float s, int c, float t) {
        drawer.drawShape(posOnCamera(pos), angle, color, s, c, t);
    }

    @Override
    public void fillShape(Vec2D pos, Angle angle, Color color, float s, int c) {
        drawer.fillShape(posOnCamera(pos), angle, color, s, c);
    }

    @Override
    public void fillShape(Vec2D pos, Angle angle, Color colF, Color colD, float s, int c, float t) {
        drawer.fillShape(posOnCamera(pos), angle, colF, colD, s, c, t);
    }

    @Override
    public void drawRect(Vec2D pos, Vec2D size, Color color, Angle angle, float t) {
        drawer.drawRect(posOnCamera(pos), size, color, angle, t);
    }

    @Override
    public void fillRect(Vec2D pos, Vec2D size, Color color, Angle angle) {
        drawer.fillRect(posOnCamera(pos), size, color, angle);
    }

    @Override
    public void fillRect(Vec2D pos, Vec2D size, Color colF, Color colD, Angle angle, float t) {
        drawer.fillRect(posOnCamera(pos), size, colF, colD, angle, t);
    }

    @Override
    public void drawLine(Vec2D v1, Vec2D v2, Color color, float thickness) {
        drawer.drawLine(posOnCamera(v1), posOnCamera(v2), color, thickness);
    }

    @Override
    public void drawCircle(Vec2D pos, float radius, Color color, float t) {
        drawer.drawCircle(posOnCamera(pos), radius, color, t);
    }

    @Override
    public void fillCircle(Vec2D pos, float radius, Color color) {
        drawer.fillCircle(posOnCamera(pos), radius, color);
    }

    @Override
    public void fillCircle(Vec2D pos, float radius, Color colF, Color colD, float t) {
        drawer.fillCircle(posOnCamera(pos), radius, colF, colD, t);
    }

    @Override
    public void drawImage(Vec2D pos, Image image) {
        drawer.drawImage(posOnCamera(pos), image);
    }

    @Override
    public void drawImage(Vec2D pos, Image image, Angle angle) {
        drawer.drawImage(posOnCamera(pos), image, angle);
    }

    @Override
    public void drawImage(Vec2D pos, Image image, Angle angle, Vec2D offset) {
        drawer.drawImage(posOnCamera(pos), image, angle, offset);
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
