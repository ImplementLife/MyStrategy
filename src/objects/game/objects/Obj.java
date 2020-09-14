package objects.game.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class Obj implements Updatable, Drawn, Comparable {

    private Id id;
    protected boolean removed;

    //==============================================//

    public Obj(ObjTypes type) {
        id = new Id(type);
        put(this);
    }

    //==============================================//

    public final Id getId() {
        return new Id(id);
    }

    public void remove() {
        remove(this);
        removed = true;
    }

    //==============================================//

    @Override
    public final int compareTo(Object arg) {
        return id.compareTo(arg);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    /*============================================================================================*/
    /*============================================================================================*/
    /*============================================================================================*/

//    private static TreeMap<Id, Obj> objects = new TreeMap<>();
    private static ConcurrentSkipListMap<Id, Obj> objects = new ConcurrentSkipListMap<>();

    //==============================================//

    public static synchronized Collection<Obj> getObj() {
        return objects.values();
    }

    public static synchronized Collection<Obj> getObjFromId(Collection<Id> listId) {
        Collection<Obj> returnedListObj = new ArrayList<>();
        for (Id id : listId) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }

    public static synchronized Collection<Obj> getObjFromType(ObjTypes unitType) {
        Collection<Obj> returnedListObj = new ArrayList<>();
        Collection<Id> keySet = objects.keySet();
        for (Id id : keySet) if (id.getType() == unitType.Type) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }

    public static synchronized Obj getObjFromId(Id id) {
        Obj obj = objects.get(id);
        if (obj == null) obj = objFromAdd.get(id);
        return obj;
    }

    //==============================================//

    private static ArrayList<Id> idFromClear = new ArrayList<>();

    public static void remove(Obj obj) {
        idFromClear.add(obj.getId());
    }

    private static void clearObjects() {
        for (Id id : idFromClear) {
            objects.remove(id);
            //id.isFree();
        }
        idFromClear.clear();
    }

    //==============================================//

    private static TreeMap<Id, Obj> objFromAdd = new TreeMap<>();

    public static void put(Obj obj) {
        objFromAdd.put(obj.getId(), obj);
    }

    private static void addObjects() {
        objects.putAll(objFromAdd);
        objFromAdd.clear();
    }

    //==============================================//

    public static void updateList() {
        clearObjects();
        addObjects();
    }

}

