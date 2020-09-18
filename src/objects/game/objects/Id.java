package objects.game.objects;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

public final class Id implements Comparable {
    //==========     Static     =============//

    private static class List extends ArrayList<Id> {
        private int countNum = 0;
        private int getUnitNum() { return countNum++; }
    }
    private static TreeMap<ObjTypes, List> listsId;
//    private static TreeMap<Boolean, Id> mapId;

    static {
        listsId = new TreeMap<>();
        for (ObjTypes type : ObjTypes.values()) listsId.put(type, new List());
    }

    //=======================================//

    private byte type;
    private int num;

    Id(ObjTypes type) {
        this.type = type.type;
        this.num = listsId.get(type).getUnitNum();
    }
    public Id(Id id) {
        this.type = id.type;
        this.num = id.num;
    }

    public byte getType() {
        return type;
    }

    //=======================================//

    @Override
    public String toString() {
        return "type: " + type + "; unitNum: " + num + ";";
    }

    @Override
    public final int compareTo(Object arg) {
        if (((Id)arg).type != this.type) {
            return Math.round(this.type - ((Id)arg).type);
        } else {
            return Math.round(this.num - ((Id)arg).num);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return type == id.type && num == id.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, num);
    }

}
