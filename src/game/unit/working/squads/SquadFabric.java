package game.unit.working.squads;

import lib.math.Vec2D;
import game.unit.working.human.Human;
import game.unit.working.vehicle.tank.Tank;

public final class SquadFabric {
    private SquadFabric(){}

    public static Squad createTankSquad(Vec2D pos, int count, float radius, int player) {
        Squad squad = new Squad();
        for (int i = 0; i < count; i++) {
            Tank tank = new Tank(Vec2D.newRandomVec(pos, radius), 0);
            tank.setPlayer(player);
            squad.putMembers(tank);

        }
        squad.setPlayer(player);

        squad.updateButtonPos();
        return squad;
    }
    public static Squad createHumanSquad(Vec2D pos, int count, float radius, int player) {
        Squad squad = new Squad();
        for (int i = 0; i < count; i++) {
            Human human = new Human(Vec2D.newRandomVec(pos, radius));
            human.setPlayer(player);
            squad.putMembers(human);
        }
        squad.setPlayer(player);

        squad.updateButtonPos();
        return squad;
    }
}
