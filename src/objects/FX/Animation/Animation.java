package objects.FX.Animation;

import draw.ImageLoader;
import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import lib.timer.Timer;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Animation extends Obj {
    private static HashMap<String, Image[]> allImages = new HashMap<>();
    private static final String PATH = "resource/images/animations/";
    private static final String SUFFIX = ".gif";

    static {
        String name = PATH + "exp" + SUFFIX;
        try { allImages.put(name, ImageLoader.loadGif(new File(name)));
        } catch (IOException e) { e.printStackTrace(); }
    }

    private final Vec2D pos;
    private final Timer pause;
    private Image[] images;
    private int currentImage;
    private boolean loop, remove;

    public Animation(final Vec2D pos, String name, int pause, boolean loop) {
        super(ObjTypes.EXPLOSION);
        this.pos = pos;
        this.pause = new Timer(pause);
        this.images = allImages.get(PATH + name + SUFFIX);
        this.loop = loop;
    }
    public Animation(final Vec2D pos, String name, boolean loop) {
        this(pos, name, 40, loop);
    }

    public boolean isRemove() {
        return remove;
    }

    public void draw(GameDrawer drawer) {
        if (images != null) {
            if (currentImage+1 >= images.length) {
                if (loop) currentImage = 0;
                else remove = true;
            } else if (pause.startF()) {
                currentImage++;
            }
            drawer.drawImage(pos, images[currentImage]);
        }
    }
}
