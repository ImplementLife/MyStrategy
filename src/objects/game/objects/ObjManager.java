package objects.game.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public final class ObjManager {
    //==========     Static     =============//
    private static ObjManager objManager;

    public static ObjManager getObjManager() {
        if (objManager == null) objManager = new ObjManager();
        return objManager;
    }

    //==============================================//
    private TreeMap<Id, Obj> objects;
    private ArrayList<Id> idFromClear;
    private TreeMap<Id, Obj> objFromAdd;

    //==============================================//
    private ObjManager() {
        objects = new TreeMap<>();
        objFromAdd = new TreeMap<>();
        idFromClear = new ArrayList<>();
    }

    //==============================================//
    public synchronized Collection<Obj> getObj() {
        return objects.values();
    }
    public synchronized Collection<Obj> getObjFromType(ObjTypes unitType) {
        Collection<Obj> returnedListObj = new ArrayList<>();
        Collection<Id> keySet = objects.keySet();
        for (Id id : keySet) if (id.getType() == unitType.type) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }
    public synchronized Collection<Obj> getObjFromId(Collection<Id> listId) {
        Collection<Obj> returnedListObj = new ArrayList<>();
        for (Id id : listId) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }
    public synchronized Obj getObjFromId(Id id) {
        Obj obj = objects.get(id);
        if (obj == null) obj = objFromAdd.get(id);
        return obj;
    }

    public synchronized void updateList() {
        //Clear Obj
        try {
            for (Id id : idFromClear) objects.remove(id);
            idFromClear.clear();
        } catch (Exception e) { e.printStackTrace(); }

        //Add Obj
        try {
            objects.putAll(objFromAdd);
            objFromAdd.clear();
        } catch (Exception e) { e.printStackTrace(); }
    }

    //==============================================//
    public void remove(Obj obj) {
        idFromClear.add(obj.getId());
    }
    public void put(Obj obj) {
        objFromAdd.put(obj.getId(), obj);
    }
}
