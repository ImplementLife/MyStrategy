package lib;

import java.util.HashMap;

public class Data<T> {
    private HashMap<Integer, T> data;
//    private Hashtable<Integer, T> data2;

    public Data() {
        data = new HashMap<>();
    }

    public T getData(int hash) throws Exception {
        T instance = data.get(hash);
        if (instance == null) throw new Exception("Instance not found.");
        else return instance;
    }

    public T getData(T t) throws Exception {
        return getData(t.hashCode());
    }

    public T put(T t) {
        return data.put(t.hashCode(), t);
    }
}
