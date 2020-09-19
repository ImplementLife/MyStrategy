package draw.FX.Animation;

import draw.drawer.Draw;
import draw.drawer.GameDrawer;
import draw.resource.Resource;
import game.objects.GameObject;
import lib.file.ImageLoader;
import lib.math.Vec2D;
import lib.timer.Timer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Animation extends GameObject {
    private static final String PATH = "resource/images/animations/";
    private static final String SUFFIX = ".gif";

    private final Vec2D pos;
    private final Timer pause;
    private final Image[] images;
    private int currentImage;
    private boolean loop;

    public Animation(final Vec2D pos, String name, int pause, boolean loop) {
        this.pos = pos;
        this.pause = new Timer(pause);
        this.images = Resource.getResImageArray().get(PATH + name + SUFFIX);
        this.loop = loop;
    }
    public Animation(final Vec2D pos, String name, boolean loop) {
        this(pos, name, 40, loop);
    }

    public void draw(Draw drawer) {
        if (images != null) {
            if (currentImage+1 >= images.length) {
                if (loop) currentImage = 0;
                else remove();
            } else if (pause.startF()) {
                currentImage++;
            }
            drawer.drawImage(pos, images[currentImage]);
        }
    }
}
