package objects.game.objects;

import draw.drawer.GameDrawer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class DrawManager {
    private static DrawManager drawManager;
    public static DrawManager getDrawManager() {
        if (drawManager == null) drawManager = new DrawManager();
        return drawManager;
    }

    //==============================================//
    private TreeMap<Integer, HashSet<Draw>> mapDrawSets;
    private ArrayList<Draw> tempAdd;
    private ArrayList<Draw> tempRemove;

    //==============================================//
    private DrawManager() {
        this.mapDrawSets = new TreeMap<>();
        this.tempAdd = new ArrayList<>();
        this.tempRemove = new ArrayList<>();
    }

    public void iterate(GameDrawer gameDrawer) {
        synchronized (this) {
            for (Draw draw : tempRemove) {
                if (mapDrawSets.containsKey(draw.layer)) mapDrawSets.get(draw.layer).remove(draw);
            }

            for (Draw draw : tempAdd) {
                if (!mapDrawSets.containsKey(draw.layer)) mapDrawSets.put(draw.layer, new HashSet<>());
                mapDrawSets.get(draw.layer).add(draw);
            }

            tempRemove.clear();
            tempAdd.clear();
        }

        for (HashSet<Draw> set : mapDrawSets.values()) {
            for (Draw d : set) {
                try {
                    d.draw(gameDrawer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void put(Draw o) {
        tempAdd.add(o);
    }
    public synchronized void remove(Draw o) {
        tempRemove.add(o);
    }

    //==============================================//
    static class Draw {
        private Integer layer;
        private final Obj target;
        private boolean draw;

        //==============================================//
        public Draw(Obj target) {
            this(target, 0);
        }
        public Draw(Obj target, Integer layer) {
            this.target = target;
            setLayer(layer);
        }

        //==============================================//
        public Integer getLayer() {
            return layer;
        }
        public void setLayer(Integer layer) {
            this.layer = layer;
            getDrawManager().put(this);
            remove();
        }

        public void setDraw(boolean draw) {
            this.draw = draw;
        }
        public void remove() {
            getDrawManager().remove(this);
        }

        public void draw(GameDrawer drawer) {
            if (draw) target.draw(drawer);
        }
    }
}
