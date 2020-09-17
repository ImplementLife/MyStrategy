package draw.drawer;

import lib.math.Angle;
import lib.math.Vec2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

/**
 * Делегируй методы
 */
public class Drawer {
    private final BufferedImage image;
    private final Graphics2D g;

    private Vec2D size;
    private final Stroke defaultStroke;

    public Drawer(BufferedImage image, Graphics2D g) {
        this.image = image;
        this.g = g;
        this.defaultStroke = g.getStroke();
    }
    public Drawer(BufferedImage image) {
        this(image, image.createGraphics());
    }
    public Drawer(Vec2D size, boolean alpha) {
        this(new BufferedImage(size.getIntX(), size.getIntY(), alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB));
        if (alpha) g.setBackground(new Color(0, true));
    }

    public BufferedImage getImage() {
        return image;
    }

    public Graphics2D getG() {
        return g;
    }

    public void setSize(Vec2D size) {
        this.size = size;
    }

    public void dispose() {
        if (size != null) g.clearRect(0,0, size.getIntX(), size.getIntY());
        else g.clearRect(0,0, image.getWidth(), image.getHeight());
    }

    /*===   Delegate Graphics2D   */
    private void drawAngle(Vec2D pos, Angle angle, Runnable target) {
        AffineTransform origForm = g.getTransform();
        AffineTransform newForm = (AffineTransform)(origForm.clone());
        newForm.rotate(angle.getValue(), pos.getX(), pos.getY());
        g.setTransform(newForm);
        target.run();
        g.setTransform(origForm);
    }

    private void draw(Shape shape, float thickness) {
        g.setStroke(new BasicStroke(thickness));
        g.draw(shape);
    }
    private void draw(Shape shape, float thickness, Color color) {
        g.setColor(color);
        draw(shape, thickness);
    }

    private void fill(Shape shape, Color color) {
        g.setColor(color);
        g.fill(shape);
    }

    ///   Draw / Fill pentagon
    private Path2D.Float getPentagon(Vec2D pos, float size) {
        Path2D.Float triangle = new Path2D.Float();

        Vec2D pos1 = new Vec2D(pos);
        pos1.subY((size + (size * Math.sqrt(3))/2)/2);
        triangle.moveTo(pos1.getX(), pos1.getY());

        Vec2D pos2 = pos1.addAngVec(size, Math.toRadians(150));
        triangle.lineTo(pos2.getX(), pos2.getY());

        Vec2D pos3 = pos2.addY(size);
        triangle.lineTo(pos3.getX(), pos3.getY());

        Vec2D pos4 = pos3.subX(size);
        triangle.lineTo(pos4.getX(), pos4.getY());

        Vec2D pos5 = pos4.subY(size);
        triangle.lineTo(pos5.getX(), pos5.getY());

        triangle.closePath();

        return triangle;
    }

    public void drawPentagon(Vec2D pos, Angle angle, Color color, float size, float thickness) {
        g.setColor(color);
        drawAngle(pos, angle, () -> draw(getPentagon(pos, size), thickness));
    }
    public void fillPentagon(Vec2D pos, Angle angle, Color color, float size) {
        g.setColor(color);
        drawAngle(pos, angle, () -> g.fill(getPentagon(pos, size)));
    }

    ///   Draw / Fill Rect
    private Path2D.Float getRect(Vec2D pos, Vec2D size) {
        Path2D.Float triangle = new Path2D.Float();

        Vec2D pos1 = new Vec2D(pos);
        triangle.moveTo(pos1.getX(), pos1.getY());

        Vec2D pos2 = pos1.addX(size.getX());
        triangle.lineTo(pos2.getX(), pos2.getY());

        Vec2D pos3 = pos1.addY(size.getY());
        triangle.lineTo(pos3.getX(), pos3.getY());

        Vec2D pos4 = pos1.subX(size.getX());
        triangle.lineTo(pos4.getX(), pos4.getY());

        triangle.closePath();
        return triangle;
    }

    public void drawRect(Vec2D pos, Vec2D size, Color color, float thickness) {
        draw(getRect(pos, size), thickness, color);
    }
    public void drawRect(Vec2D pos, Vec2D size, Color color, float thickness, Angle angle) {
        drawAngle(pos, angle, () -> draw(getRect(pos, Vec2D.scalar(size, 0.5)), thickness, color));
    }

    public void fillRect(Vec2D pos, Vec2D size, Color color) {
        fill(getRect(pos, size), color);
    }
    public void fillRect(Vec2D pos, Vec2D size, Color color, Angle angle) {
        drawAngle(pos, angle, () -> fill(getRect(Vec2D.sub(pos, Vec2D.scalar(size, 0.5)), size), color));
    }

    ///   Draw / Fill triangle
    private Path2D.Float getTriangle(Vec2D pos, float size) {
        Path2D.Float triangle = new Path2D.Float();

        Vec2D pos1 = new Vec2D(pos);
        pos1.subY((size * Math.sqrt(3))/4);
        triangle.moveTo(pos1.getX(), pos1.getY());

        Vec2D pos2 = pos1.addAngVec(size, Math.toRadians(150));
        triangle.lineTo(pos2.getX(), pos2.getY());

        Vec2D pos3 = pos2.subX(size);
        triangle.lineTo(pos3.getX(), pos3.getY());

        triangle.closePath();
        return triangle;
    }

    public void drawTriangle(Vec2D pos, Angle angle, float size, float thickness, Color color) {
        drawAngle(pos, angle, () -> draw(getTriangle(pos, size), thickness, color));
    }
    public void fillTriangle(Vec2D pos, Angle angle, float size, Color color) {
        drawAngle(pos, angle, () -> fill(getTriangle(pos, size), color));
    }

    ///   Draw Line
    public void drawLine(Vec2D v1, Vec2D v2, Color color, float thickness) {
        draw(new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY()), thickness, color);
    }

    ///   Fill Circle
    private Ellipse2D.Double getCircle(Vec2D pos, float radius) {
        Ellipse2D.Double circle = new Ellipse2D.Double();
        circle.x = pos.getX() - radius;
        circle.y = pos.getY() - radius;
        circle.width = radius*2;
        circle.height = radius*2;
        return circle;
    }

    public void fillCircle(Vec2D pos, float radius, Color color) {
        fill(getCircle(pos, radius), color);
    }
    public void drawCircle(Vec2D pos, float radius, float thickness, Color color) {
        draw(getCircle(pos, radius), thickness, color);
    }

    ///   Draw Image
    public void drawImage(Vec2D pos, Image image) {
        g.drawImage(image, pos.getIntX(), pos.getIntY(), null);
    }
    public void drawImage(Vec2D pos, Image image, float angle) {
        Vec2D offset = new Vec2D(image.getWidth(null), image.getHeight(null)).scalar(0.5);
        drawImage(pos, offset, image, angle);
    }
    public void drawImage(Vec2D pos, Vec2D offset, Image image, float angle) {
        drawAngle(pos, new Angle(angle), () -> drawImage(Vec2D.sub(pos, offset), image));
    }

    ///   Draw String
    public void drawString(Vec2D pos, String text, int size, Color color) {
        g.setColor(color);
//        Font oldFont = g.getFont();
        g.setFont(new Font(null, Font.PLAIN, size));
        g.drawString(text, pos.getIntX(), pos.getIntY());
    }
    public void drawString(Vec2D pos, String[] text, int size, Color color) {
        Vec2D vec = pos.clone().subY(4);
        for (String str : text) drawString(vec.addY(size + 2), str, size, color);
    }

    ///===   Settings
    public void setAntialiasing(SettingsG value) {
        switch (value) {
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); break;
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); break;
            default:      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }
    }
    public void setRendering(SettingsG value) {
        switch (value) {
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); break;
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); break;
            default: g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        }
    }

    public void setDithering(SettingsG value) {
        switch (value) {
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE); break;
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE); break;
            default:      g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_RENDER_DEFAULT);
        }
    }
    public void setTextAntialiasing(SettingsG value) {
        switch (value) {
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); break;
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); break;
            default:      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        }
    }

    public void setFractionalMetrics(SettingsG value) {
        switch (value) {
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON); break;
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF); break;
            default: g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        }
    }
    public void setAlphaInterpolation(SettingsG value) {
        switch (value) {
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED); break;
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY); break;
            default:      g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        }
    }

    public void setColorRendering(SettingsG value) {
        switch (value) {
            case SPEED:   g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED); break;
            case QUALITY: g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY); break;
            default: g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        }
    }

    public void setInterpolation(Object value) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, value);
    }
    public void setResolutionVariant(Object value) {
        g.setRenderingHint(RenderingHints.KEY_RESOLUTION_VARIANT, value);
    }
    public void setStrokeControl(Object value) {
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, value);
    }

    public void setAll(SettingsDrawer settingsDrawer) {
        g.setRenderingHints(settingsDrawer.getSettings());
    }
}
