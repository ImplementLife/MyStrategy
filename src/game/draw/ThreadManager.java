package game.draw;

import lib.threads.WhileThreadESP;

import java.util.concurrent.Semaphore;

public final class ThreadManager {
    //==========     Static     =============//
    private static ThreadManager threadManager;
    public static ThreadManager getThreadManager() {
        if (threadManager == null) threadManager = new ThreadManager();
        return threadManager;
    }

    //=======================================//
    private Semaphore semaphore;
    public final WhileThreadESP drawObj;
    public final WhileThreadESP drawMap;
    public final WhileThreadESP drawFinal;

    private ThreadManager() {
        semaphore = new Semaphore(2, true);
        drawMap   = new WhileThreadESP(semaphore, 1);
        drawObj   = new WhileThreadESP(semaphore, 1);
        drawFinal = new WhileThreadESP(semaphore, 2);

        drawMap.setName("drawMap thread");
        drawObj.setName("drawObj thread");
        drawFinal.setName("DrawFinal thread");
    }

    private boolean run;
    public void start() {
        if (!run) {
            run = true;
            try {
                drawMap.start();
                drawObj.start();
                drawMap.sleep(1);
                drawObj.sleep(1);
                drawMap.yield();
                drawObj.yield();
                drawFinal.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop() {
        if (run) {
            drawMap.stopped();
            drawObj.stopped();
            drawFinal.stopped();
        }
    }

}
