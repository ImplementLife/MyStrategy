package draw.drawer;

import java.awt.*;
import java.util.HashMap;

public class SettingsDrawer {
    private HashMap<Object, Object> settings;

    public SettingsDrawer() {
        this.settings = new HashMap<>();
    }
    //===   Settings

    public void setAntialiasing(SettingsG value) {
        switch (value) {
            case QUALITY: settings.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); break;
            case SPEED:   settings.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); break;
            default:      settings.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }
    }
    public void setRendering(SettingsG value) {
        switch (value) {
            case SPEED:   settings.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED); break;
            case QUALITY: settings.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); break;
            default:      settings.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
        }
    }

    public void setDithering(SettingsG value) {
        switch (value) {
            case QUALITY: settings.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE); break;
            case SPEED:   settings.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE); break;
            default:      settings.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_RENDER_DEFAULT);
        }
    }
    public void setTextAntialiasing(SettingsG value) {
        switch (value) {
            case QUALITY: settings.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); break;
            case SPEED:   settings.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF); break;
            default:      settings.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        }
    }

    public void setFractionalMetrics(SettingsG value) {
        switch (value) {
            case QUALITY: settings.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON); break;
            case SPEED:   settings.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF); break;
            default:      settings.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
        }
    }
    public void setAlphaInterpolation(SettingsG value) {
        switch (value) {
            case SPEED:   settings.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED); break;
            case QUALITY: settings.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY); break;
            default:      settings.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        }
    }

    public void setColorRendering(SettingsG value) {
        switch (value) {
            case SPEED:   settings.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED); break;
            case QUALITY: settings.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY); break;
            default:      settings.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
        }
    }

    public void setInterpolation(Object value) {
        settings.put(RenderingHints.KEY_INTERPOLATION, value);
    }
    public void setResolutionVariant(Object value) {
        settings.put(RenderingHints.KEY_RESOLUTION_VARIANT, value);
    }
    public void setStrokeControl(Object value) {
        settings.put(RenderingHints.KEY_STROKE_CONTROL, value);
    }

    public HashMap<Object, Object> getSettings() {
        return settings;
    }
}
