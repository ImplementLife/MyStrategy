package draw.resource;

import lib.file.FileLoader;
import lib.file.ImageLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Resource<T> {
    private static Resource<Image[]> resImageArray;
    private static Resource<Image> resImage;

    public static Resource<Image[]> getResImageArray() {
        return resImageArray;
    }
    public static Resource<Image> getResImage() {
        return resImage;
    }

    public static void init() {
        resImageArray = new Resource<>();


        String path = "resource/images/animations/";
        File dir = new File(path);

        for (File f : FileLoader.getFileList(dir, ".gif")) {
            try { resImageArray.put(path + f.getName(), ImageLoader.loadGif(f));
            } catch (IOException e) { e.printStackTrace(); }
        }
        resImage = new Resource<>();
    }

    private Map<String, T> resources;
    private Resource() {
        this.resources = new HashMap<>();
    }

    public T get(String s) {
        return resources.get(s);
    }
    public void putAll(Map<String, ? extends T> map) {
        resources.putAll(map);
    }
    public T put(String s, T t) {
        return resources.put(s, t);
    }
}
