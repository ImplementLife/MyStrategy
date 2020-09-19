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
public final class Drawer implements Draw {
    private final BufferedImage image;
    private final Graphics2D g;

    public Drawer(BufferedImage image, Graphics2D g) {
        this.image = image;
        this.g = g;
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

    public void dispose() {
        int w = (int) Math.ceil(image.getWidth() / g.getTransform().getScaleX());
        int h = (int) Math.ceil(image.getHeight() / g.getTransform().getScaleY());
        g.clearRect(0,0, w, h);
    }

    /*===   Delegate Graphics2D   */
    private void forAngle(Vec2D pos, Angle angle, Runnable target) {
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

    //======================   Get Shape
    private Path2D.Double getShape(Vec2D vec[]) {
        Path2D.Double shape = new Path2D.Double();
        shape.moveTo(vec[0].getX(), vec[0].getY());
        for (int i = 1; i < vec.length; i++) {
            shape.lineTo(vec[i].getX(), vec[i].getY());
        }
        shape.closePath();
        return shape;
    }
    private Path2D.Float getPentagon(Vec2D pos, float size) {
        Path2D.Float triangle = new Path2D.Float();

        Vec2D pos1 = new Vec2D(pos);
        pos1.subY((size*2/3 + (size * Math.sqrt(3))/2)/2);
        triangle.moveTo(pos1.getX(), pos1.getY());

        Vec2D pos2 = pos1.addAngVec(size, Math.toRadians(150));
        triangle.lineTo(pos2.getX(), pos2.getY());

        Vec2D pos3 = pos2.addY(size*2/3);
        triangle.lineTo(pos3.getX(), pos3.getY());

        Vec2D pos4 = pos3.subX(size);
        triangle.lineTo(pos4.getX(), pos4.getY());

        Vec2D pos5 = pos4.subY(size*2/3);
        triangle.lineTo(pos5.getX(), pos5.getY());

        triangle.closePath();
        return triangle;
    }
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
    private Ellipse2D.Double getCircle(Vec2D pos, float radius) {
        Ellipse2D.Double circle = new Ellipse2D.Double();
        circle.x = pos.getX() - radius;
        circle.y = pos.getY() - radius;
        circle.width = radius*2;
        circle.height = radius*2;
        return circle;
    }

    //======================   Draw String

    @Override
    public void drawString(Vec2D pos, String text, int size, Color color) {
        g.setColor(color);
//        Font oldFont = g.getFont();
        g.setFont(new Font(null, Font.PLAIN, size));
        g.drawString(text, pos.getIntX(), pos.getIntY());
    }

    @Override
    public void drawString(Vec2D pos, String[] text, int size, Color color) {
        Vec2D vec = pos.clone().subY(4);
        for (String str : text) drawString(vec.addY(size + 2), str, size, color);
    }

    //======================   Draw Shape

    @Override
    public void drawShape(Vec2D pos, Angle angle, Color color, float s, int c, float t) {
        Shape shape = null;
        switch (c) {
            case 3: shape = getTriangle(pos, s); break;
            case 4: shape = getRect(pos, new Vec2D(s, s)); break;
            case 5: shape = getPentagon(pos, s); break;
        }
        Shape finalShape = shape;
        forAngle(pos, angle, () -> draw(finalShape, t, color));
    }

    @Override
    public void fillShape(Vec2D pos, Angle angle, Color color, float s, int c) {
        Shape shape = null;
        switch (c) {
            case 3: shape = getTriangle(pos, s); break;
            case 4: shape = getRect(pos, new Vec2D(s, s)); break;
            case 5: shape = getPentagon(pos, s); break;
        }
        Shape finalShape = shape;
        forAngle(pos, angle, () -> fill(finalShape, color));
    }

    @Override
    public void fillShape(Vec2D pos, Angle angle, Color colF, Color colD, float s, int c, float t) {
        fillShape(pos, angle, colF, s, c);
        if (t > 0) drawShape(pos, angle, colD, s, c, t);
    }

    //======================   Draw Rect

    @Override
    public void drawRect(Vec2D pos, Vec2D size, Color color, Angle angle, float t) {
        if (angle.getValue() == 0) draw(getRect(pos, size), t, color);
        else forAngle(pos, angle, () -> draw(getRect(Vec2D.sub(pos, Vec2D.scalar(size, 0.5)), size), t, color));
    }

    @Override
    public void fillRect(Vec2D pos, Vec2D size, Color color, Angle angle) {
        if (angle.getValue() == 0) fill(getRect(pos, size), color);
        else forAngle(pos, angle, () -> fill(getRect(Vec2D.sub(pos, Vec2D.scalar(size, 0.5)), size), color));
    }

    @Override
    public void fillRect(Vec2D pos, Vec2D size, Color colF, Color colD, Angle angle, float t) {
        fillRect(pos, size, colF, angle);
        if (t > 0) drawRect(pos, size, colD, angle, t);
    }

    //======================   Draw Line

    @Override
    public void drawLine(Vec2D v1, Vec2D v2, Color color, float thickness) {
        draw(new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY()), thickness, color);
    }

    //======================   Draw Circle

    @Override
    public void drawCircle(Vec2D pos, float radius, Color color, float t) {
        draw(getCircle(pos, radius), t, color);
    }

    @Override
    public void fillCircle(Vec2D pos, float radius, Color color) {
        fill(getCircle(pos, radius), color);
    }

    @Override
    public void fillCircle(Vec2D pos, float radius, Color colF, Color colD, float t) {
        Shape circle = getCircle(pos, radius);
        fill(circle, colF);
        if (t > 0) draw(circle, t, colD);
    }

    //======================   Draw Image

    @Override
    public void drawImage(Vec2D pos, Image image) {
        g.drawImage(image, pos.getIntX(), pos.getIntY(), null);
    }

    @Override
    public void drawImage(Vec2D pos, Image image, Angle angle) {
        Vec2D offset = new Vec2D(image.getWidth(null), image.getHeight(null)).scalar(0.5);
        drawImage(pos, image, angle, offset);
    }

    @Override
    public void drawImage(Vec2D pos, Image image, Angle angle, Vec2D offset) {
        forAngle(pos, angle, () -> drawImage(Vec2D.sub(pos, offset), image));
    }

    //======================   Settings
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
