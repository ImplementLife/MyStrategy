package game.unit.working.vehicle.train;

import draw.drawer.Draw;
import game.objects.GameObject;
import game.objects.managers.GameObjectTypes;

import java.util.LinkedList;

public class RollingStock extends GameObject {
    //==========     Static     =============//
    private static final GameObjectTypes TYPE = GameObjectTypes.ROLLING_STOCK;

    //=======================================//

    private Wagon firstWagon;                      //Первый вагон состава
    private LinkedList<Wagon> intermediateWagons;  //Промежуточные вагоны состава
    private Wagon lastWagon;                       //Последний вагон состава






    public RollingStock() {
        super(TYPE);



    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Draw drawer) {

    }
}
