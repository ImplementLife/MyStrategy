package objects.FX.Animation;

import lib.Timer;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Animation {
    private ArrayList<Image> images;
    private int currentImage;
    private Timer loadTimer;

    public Animation(ArrayList<Image> images, Timer loadTimer) {
        this.images = images;
        this.loadTimer = loadTimer;
    }
    public Animation(File fileJSON) {

    }

    public void update() {
        if (loadTimer.start() && images.size() != currentImage) {
            currentImage++;
        }
    }

    public Image getCurrentImage() {
        return images.get(currentImage);
    }
}
