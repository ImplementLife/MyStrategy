package game.unit.working.vehicle;

import game.objects.managers.GameObjectTypes;
import game.unit.working.Unit;

import java.util.HashMap;

public abstract class Vehicle extends Unit {
    //==========     Static     =============//
    private static HashMap<Integer, Schema> mapSchemas = new HashMap<>();

    private static class Schema {

    }
    //=======================================//


    public Vehicle(GameObjectTypes type) {
        super(type);
    }
}
