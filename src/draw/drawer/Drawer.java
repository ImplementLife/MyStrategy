package draw.drawer;

import lib.math.Angle;
import lib.math.Vec2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;

/**
 * Делегируй методы
 */
public class Drawer {
    private final BufferedImage image;
    private final Graphics2D g;
    private boolean alpha;
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
        this.alpha = alpha;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Graphics2D getG() {
        return g;
    }

    public void dispose() {
        if (alpha) g.setBackground(new Color(0, true));
        if (size != null) g.clearRect(0,0, size.getIntX(), size.getIntY());
        else g.clearRect(0,0, image.getWidth(), image.getHeight());
    }

    public void setSize(Vec2D size) {
        this.size = size;
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

    ///   Draw / Fill pentagon

    private static class D extends Path2D.Double {
        private static int TRIANGLE = 1;
        private static int PENTAGON = 2;

        private Vec2D pos;
        private float size;
        private int type;

        public D(Vec2D pos, float size, int type) {
            this.pos = pos;
            this.size = size;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            D d = (D) o;
            return java.lang.Float.compare(d.size, size) == 0 &&
                    type == d.type &&
                    Objects.equals(pos, d.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pos, size, type);
        }
    }
    private static HashMap<Integer, D> dHashSet = new HashMap<>();

    public Path2D.Double getPentagon(Vec2D pos, float size) {
        Path2D.Double triangle = new Path2D.Double();

        Vec2D pos1 = new Vec2D(pos);
        pos1.subY((size + (size * Math.sqrt(3))/2)/2);
        triangle.moveTo(pos1.getX(), pos1.getY());

        Vec2D pos2 = Vec2D.newAngVec(pos1, size, Math.toRadians(150));
        triangle.lineTo(pos2.getX(), pos2.getY());

        Vec2D pos3 = new Vec2D(pos2).addY(size);
        triangle.lineTo(pos3.getX(), pos3.getY());

        Vec2D pos4 = new Vec2D(pos3).subX(size);
        triangle.lineTo(pos4.getX(), pos4.getY());

        Vec2D pos5 = new Vec2D(pos4).subY(size);
        triangle.lineTo(pos5.getX(), pos5.getY());

        triangle.closePath();

        return triangle;
    }

    public void drawPentagon(Vec2D pos, Angle angle, float size, float thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        drawAngle(pos, angle, () -> g.draw(getPentagon(pos, size)));
    }
    public void fillPentagon(Vec2D pos, Angle angle, float size, Color color) {
        g.setColor(color);
        drawAngle(pos, angle, () -> g.fill(getPentagon(pos, size)));
    }

    ///   Draw / Fill triangle

    private Path2D.Double getTriangle(Vec2D pos, float size) {
        Path2D.Double triangle = new Path2D.Double();

        Vec2D pos1 = new Vec2D(pos);
        pos1.subY((size * Math.sqrt(3))/4);
        triangle.moveTo(pos1.getX(), pos1.getY());

        Vec2D pos2 = Vec2D.newAngVec(pos1, size, Math.toRadians(150));
        triangle.lineTo(pos2.getX(), pos2.getY());

        Vec2D pos3 = new Vec2D(pos2).subX(size);
        triangle.lineTo(pos3.getX(), pos3.getY());

        triangle.closePath();
        return triangle;
    }

    public void drawTriangle(Vec2D pos, Angle angle, float size, float thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        drawAngle(pos, angle, () -> g.draw(getTriangle(pos, size)));
    }
    public void fillTriangle(Vec2D pos, Angle angle, float size, Color color) {
        g.setColor(color);
        drawAngle(pos, angle, () -> g.fill(getTriangle(pos, size)));
    }

    ///   Fill Circle

    public void fillCircle(Vec2D pos, int radius, Color color) {
        g.setColor(color);
        Vec2D posOnImage = new Vec2D(pos.getX() - (radius/2f), pos.getY() - (radius/2f));
        g.fillOval(posOnImage.getIntX(), posOnImage.getIntY(), radius, radius);
    }

    ///   Draw Line

    public void drawLine(Vec2D start, Vec2D end, int thickness, Color color) {
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        g.drawLine(start.getIntX(), start.getIntY(), end.getIntX(), end.getIntY());
        g.setStroke(defaultStroke);
    }

    ///   Draw / Fill Rect

    private void drawRect(Vec2D pos, Vec2D size) {
        g.drawRect(pos.getIntX(), pos.getIntY(), size.getIntX(), size.getIntY());
    }
    private void fillRect(Vec2D pos, Vec2D size) {
        g.fillRect(pos.getIntX(), pos.getIntY(), size.getIntX(), size.getIntY());
    }

    @FunctionalInterface
    protected interface DrawExp {
        void draw(Vec2D v1, Vec2D v2);
    }
    protected void draw(Vec2D pos, Vec2D size, Color color, DrawExp exp) {
        g.setColor(color);
        exp.draw(pos, size);
    }

    public void drawRect(Vec2D pos, Vec2D size, Color color) {
        g.setStroke(defaultStroke);
        draw(pos, size, color, this::drawRect);
    }
    public void drawRect(Vec2D pos, Vec2D size, int thickness, Color color) {
        g.setStroke(new BasicStroke(thickness));
        draw(pos, size, color, this::drawRect);
        g.setStroke(defaultStroke);
    }
    public void drawRect(Vec2D pos, Vec2D size, float angle, Color color) {
        draw(pos, size, angle, color, this::drawRect);
    }

    protected void draw(Vec2D pos, Vec2D size, float angle, Color color, DrawExp exp) {
        Vec2D reSize = Vec2D.scalar(size, 0.5);
        g.setColor(color);
        drawAngle(pos, new Angle(angle), () -> exp.draw(Vec2D.sub(pos, reSize), size));
    }

    public void fillRect(Vec2D pos, Vec2D size, Color color) {
        draw(pos, size, color, this::fillRect);
    }
    public void fillRect(Vec2D pos, Vec2D size, float angle, Color color) {
        draw(pos, size, angle, color, this::fillRect);
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

    //===   Settings

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