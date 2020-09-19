package game.update;

import draw.FX.Animation.Animation;
import lib.threads.bt.DT;
import lib.threads.bt.WhileThreadBT;
import main.game.gamePanel.listener.Listener;
import main.game.gamePanel.listener.events.Analyzer;
import game.objects.managers.UpdateManager;
import game.unit.working.squads.Squad;
import main.game.gamePanel.listener.events.Event;

import java.awt.event.KeyEvent;

public final class Updater {
    private final static WhileThreadBT thread;
    public final static DT dt;
    static {
        thread = new WhileThreadBT(() -> {
            UpdateManager.getUpdateManager().iterate();
            Squad.AttackManager.update();
        }, "Поток обновления объектов игры");
        dt = thread.getDt();
    }

    public static void start() {
        thread.start();
    }

    private static int temp = 0;

    private static Analyzer analyzer = new Analyzer(e -> {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.isReleased()) {
            thread.stopped();
            System.exit(0);
        }
        if (e.isReleased() && e.getKeyCode() == Event.LEFT_MOUSE_BUTTON) {
            if (temp < 6) temp++;
            if (temp == 5) temp = 1;
            new Animation(Listener.getGlobalMousePos().clone(), "exp_0" + temp, false);
        }
    });
}
