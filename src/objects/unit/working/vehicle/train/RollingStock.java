package objects.unit.working.vehicle.train;

import draw.drawer.GameDrawer;
import objects.game.objects.Obj;
import objects.game.objects.ObjTypes;

import java.util.LinkedList;

public class RollingStock extends Obj {
    //==========     Static     =============//
    private static final ObjTypes TYPE = ObjTypes.ROLLING_STOCK;

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
    public void draw(GameDrawer drawer) {

    }
}
