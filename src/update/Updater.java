package update;

import lib.threads.bt.DT;
import lib.threads.bt.WhileThreadBT;
import main.game.gamePanel.listener.events.Analyzer;
import objects.game.objects.Obj;
import objects.unit.working.Unit;
import objects.unit.working.squads.Squad;

import java.awt.event.KeyEvent;

public final class Updater {
    private final static WhileThreadBT thread;
    public final static DT dt;
    static {
        thread = new WhileThreadBT(() -> {
            try {
                for (Obj obj : Obj.getObj()) obj.update();
                Squad.AttackManager.update();
            } catch (Exception e) { e.printStackTrace(); }
        }, "Поток обновления объектов игры");
        dt = thread.getDt();
    }

    public static void start() {
        thread.start();
    }

    private static Analyzer analyzer = new Analyzer(e -> {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.isReleased()) {
            thread.stopped();
            System.exit(0);
        }
    });
}
