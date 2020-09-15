package objects.game.objects;

import java.util.Collection;

public abstract class Obj implements Updatable, Drawn, Comparable {
    private Id id;
    private boolean removed;

    //==============================================//

    public Obj(ObjTypes type) {
        id = new Id(type);
        put(this);
    }

    //==============================================//

    public final Id getId() {
        return new Id(id);
    }

    public final void remove() {
        remove(this);
        removed = true;
    }

    public final boolean isRemoved() {
        return removed;
    }

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
    public static Collection<Obj> getObjFromType(ObjTypes unitType) {
        return objManager.getObjFromType(unitType);
    }
    public static Collection<Obj> getObjFromId(Collection<Id> listId) {
        return objManager.getObjFromId(listId);
    }
    public static Obj getObjFromId(Id id) {
        return objManager.getObjFromId(id);
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

