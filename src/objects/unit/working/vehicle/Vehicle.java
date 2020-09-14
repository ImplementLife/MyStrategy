package objects.unit.working.vehicle;

import objects.game.objects.ObjTypes;
import objects.unit.working.Unit;

import java.util.HashMap;

public abstract class Vehicle extends Unit {
    //==========     Static     =============//
    private static HashMap<Integer, Schema> mapSchemas = new HashMap<>();

    private static class Schema {

    }
    //=======================================//


    public Vehicle(ObjTypes type) {
        super(type);
    }
}
