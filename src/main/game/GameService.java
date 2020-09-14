package main.game;

import lib.math.Vec2D;
import map.MapGenerator;
import objects.unit.working.bullet.Bullet;
import objects.unit.working.squads.SquadFabric;

public class GameService {

    private GameService() {}
    private static GameService gameService;
    public static GameService getGameService() {
        if (gameService == null) gameService = new GameService();
        return gameService;
    }

    public void createMap() {
        MapGenerator.newMap(new Vec2D(100,100), 1);
    }

    public void addUnit() {
//        new Analyzer(e -> {
//            if (e.isPressed() && e.getKeyCode() == Event.LEFT_MOUSE_BUTTON) {
//                new Bullet(new Vec2D(), Listener.getMousePos(), "resource/bullet/bullet_01.png");
//            }
//        });
        Bullet.init();
        {

            //SquadFabric.createTankSquad(new Vec2D(100, 100), 1, 0);
            Vec2D pos = new Vec2D(0, 200);
            for (int i = 0; i < 1; i++) {
                SquadFabric.createHumanSquad(pos.addX(200), 10, 200);
//                SquadFabric.createTankSquad(pos.addX(200), 3, 150);
            }
//            for (int i = 0; i < 10; i++) {
//                new Wagon(RailWay.allRailWay.get(0), 200 + i*600);
//            }
        }
    }

}