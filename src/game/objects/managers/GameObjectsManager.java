package game.objects.managers;

import game.objects.GameObject;
import game.objects.Id;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public final class GameObjectsManager {
    //==========     Static     =============//
    private static GameObjectsManager objManager;

    public static GameObjectsManager getObjManager() {
        if (objManager == null) objManager = new GameObjectsManager();
        return objManager;
    }

    //==============================================//
    private TreeMap<Id, GameObject> objects;
    private ArrayList<Id> idFromClear;
    private TreeMap<Id, GameObject> objFromAdd;

    //==============================================//
    private GameObjectsManager() {
        objects = new TreeMap<>();
        objFromAdd = new TreeMap<>();
        idFromClear = new ArrayList<>();
    }

    //==============================================//
    public synchronized Collection<GameObject> getObj() {
        return objects.values();
    }
    public synchronized Collection<GameObject> getObjFromType(GameObjectTypes unitType) {
        Collection<GameObject> returnedListObj = new ArrayList<>();
        Collection<Id> keySet = objects.keySet();
        for (Id id : keySet) if (id.getType() == unitType.type) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }
    public synchronized Collection<GameObject> getObjFromId(Collection<Id> listId) {
        Collection<GameObject> returnedListObj = new ArrayList<>();
        for (Id id : listId) returnedListObj.add(objects.get(id));
        return returnedListObj;
    }
    public synchronized GameObject getObjFromId(Id id) {
        GameObject obj = objects.get(id);
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
    public void remove(GameObject obj) {
        idFromClear.add(obj.getId());
    }
    public void put(GameObject obj) {
        objFromAdd.put(obj.getId(), obj);
    }
}
