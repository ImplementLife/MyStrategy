package update;

import lib.threads.bt.DT;
import lib.threads.bt.WhileThreadBT;
import main.game.gamePanel.listener.events.Analyzer;
import objects.game.objects.Obj;

import java.awt.event.KeyEvent;

public final class Updater {
    private final static WhileThreadBT thread;
    public final static DT dt;
    static {
        thread = new WhileThreadBT(() -> {
            for (Obj obj : Obj.getObj()) obj.update();
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
