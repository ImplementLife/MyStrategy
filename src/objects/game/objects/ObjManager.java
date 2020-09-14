package objects.game.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class ObjManager {
    //==========     Static     =============//
    private static ObjManager objManager;

    public static ObjManager getObjManager() {
        if (objManager == null) objManager = new ObjManager();
        return objManager;
    }

    //==============================================//

    private final TreeMap<Id, Obj> objects = new TreeMap<>();

    //==============================================//

    public synchronized Collection<Obj> getObj() {
        return objects.values();
    }

    public synchronized Collection<Obj> getObjFromId(Collection<Id> listId) {
        Collection<Obj> returnedListObj = new ArrayList<>();
        for (Id id : listId) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }

    public synchronized Collection<Obj> getObjFromType(ObjTypes unitType) {
        Collection<Obj> returnedListObj = new ArrayList<>();
        Collection<Id> keySet = objects.keySet();
        for (Id id : keySet) if (id.getType() == unitType.Type) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }

    public synchronized Obj getObjFromId(Id id) {
        Obj obj = objects.get(id);
        if (obj == null) obj = objFromAdd.get(id);
        return obj;
    }

    //==============================================//

    private ArrayList<Id> idFromClear = new ArrayList<>();

    public void remove(Obj obj) {
        idFromClear.add(obj.getId());
    }

    private void clearObjects() {
        for (Id id : idFromClear) {
            objects.remove(id);
            //id.isFree();
        }
        idFromClear.clear();
    }

    //==============================================//

    private TreeMap<Id, Obj> objFromAdd = new TreeMap<>();

    public void put(Obj obj) {
        objFromAdd.put(obj.getId(), obj);
    }

    private void addObjects() {
        objects.putAll(objFromAdd);
        objFromAdd.clear();
    }

    //==============================================//

    public synchronized void updateList() {
        clearObjects();
        addObjects();
    }
}
