package objects.game.objects;

import draw.drawer.GameDrawer;

import java.util.Collection;

public abstract class Obj implements Comparable {
    private ObjTypes type;
    private Id id;
    private boolean removed;
    private DrawManager.Draw draw;
    private UpdateManager.Update update;

    //==============================================//
    public Obj(ObjTypes type) {
        this.type = type;
        id = new Id(type);
        put(this);
        setDraw(true);
        setUpdate(true);
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

    public void draw(GameDrawer drawer) {}

    //==============================================//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
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
    private static ObjManager objManager = ObjManager.getObjManager();

    public static Collection<Obj> getObj() {
        return objManager.getObj();
    }
    public static void updateList() {
        objManager.updateList();
    }
    public static void remove(Obj obj) {
        objManager.remove(obj);

    }
    public static void put(Obj obj) {
        objManager.put(obj);
    }
}

