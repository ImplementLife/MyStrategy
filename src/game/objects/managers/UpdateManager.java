package game.objects.managers;

import game.objects.GameObject;

import java.util.ArrayList;
import java.util.HashSet;

public class UpdateManager {
    private static UpdateManager updateManager;
    public static UpdateManager getUpdateManager() {
        if (updateManager == null) updateManager = new UpdateManager();
        return updateManager;
    }

    //==============================================//
    private HashSet<Update> updateSet;
    private ArrayList<Update> addList;
    private ArrayList<Update> removeList;

    //==============================================//
    private UpdateManager() {
        this.updateSet = new HashSet<>();
        this.addList = new ArrayList<>();
        this.removeList = new ArrayList<>();
    }

    //==============================================//
    public void iterate() {
        synchronized (this) {
            updateSet.removeAll(removeList);
            updateSet.addAll(addList);
            removeList.clear();
            addList.clear();
        }
        for (Update target : updateSet) target.update();
    }

    public synchronized void put(Update o) {
        addList.add(o);
    }
    public synchronized void remove(Update o) {
        removeList.add(o);
    }

    //==============================================//
    public static class Update {
        private final GameObject target;

        public Update(GameObject target) {
            this.target = target;
            getUpdateManager().put(this);
        }

        public void update() {
            target.update();
        }

        public void remove() {
            getUpdateManager().remove(this);
        }
    }
}
