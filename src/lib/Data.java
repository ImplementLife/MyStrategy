package lib;

import java.util.HashMap;

public class Data<T> {
    private static HashMap<Class<?>, Data> instances = new HashMap<>();

    private HashMap<Integer, T> data;

    public Data() {
        data = new HashMap<>();
    }

//    public <E> Object getData() {
//        return (E)
//    }

}
