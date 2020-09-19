package game.objects;

import draw.drawer.Draw;
import game.objects.managers.DrawManager;
import game.objects.managers.GameObjectTypes;
import game.objects.managers.GameObjectsManager;
import game.objects.managers.UpdateManager;

import java.util.Collection;

public abstract class GameObject implements Comparable {
    private GameObjectTypes type;
    private Id id;
    private boolean removed;
    private DrawManager.Draw draw;
    private UpdateManager.Update update;

    //==============================================//
    public GameObject(GameObjectTypes type) {
        this.type = type;
        id = new Id(type);
        put(this);
        setDraw(true);
        setUpdate(true);
    }
    public GameObject() {
        this(GameObjectTypes.EXPLOSION);
    }

    //==============================================//
    public final Id getId() {
        return new Id(id);
    }

    public void remove() {
        remove(this);
        if (draw != null) draw.remove();
        if (update != null) update.remove();
        removed = true;
    }

    public final boolean isRemoved() {
        return removed;
    }

    //==============================================//
    public final void setDraw(boolean isDraw) {
        if (isDraw) {
            if (draw == null) draw = new DrawManager.Draw(this, (int) type.type);
            draw.setDraw(true);
        } else if (draw != null) draw.setDraw(false);
    }
    public final void setLayer(Integer layer) {
        draw.setLayer(layer);
    }

    public final void setUpdate(boolean isUpdate) {
        if (update == null) update = new UpdateManager.Update(this);
    }

    //==============================================//
    public void update() {}

    public void draw(Draw drawer) {}

    //==============================================//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject obj = (GameObject) o;
        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public final int compareTo(Object arg) {
        return id.compareTo(arg);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    /*============================================================================================*/
    private static GameObjectsManager objManager = GameObjectsManager.getObjManager();

    public static Collection<GameObject> getObj() {
        return objManager.getObj();
    }
    public static void updateList() {
        objManager.updateList();
    }
    public static void remove(GameObject obj) {
        objManager.remove(obj);

    }
    public static void put(GameObject obj) {
        objManager.put(obj);
    }
}

