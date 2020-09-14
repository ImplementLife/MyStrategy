package draw;

import lib.threads.WhileThreadESP;

import java.util.concurrent.Semaphore;

public final class ThreadManager {
    //==========     Static     =============//

    public static ThreadManager getThreadManager() {
        if (threadManager == null) threadManager = new ThreadManager();
        return threadManager;
    }
    private static ThreadManager threadManager;

    //=======================================//

    private Semaphore semaphore;
//    public final WhileThreadESP camera;
    public final WhileThreadESP drawObj;
    public final WhileThreadESP drawMap;
    public final WhileThreadESP drawFinal;

    private ThreadManager() {
        semaphore = new Semaphore(2, true);
//        camera    = new WhileThreadESP(semaphore, 2);
        drawMap   = new WhileThreadESP(semaphore, 1);
        drawObj   = new WhileThreadESP(semaphore, 1);
        drawFinal = new WhileThreadESP(semaphore, 2);

//        camera.setName("camera thread");
        drawMap.setName("drawMap thread");
        drawObj.setName("drawObj thread");
        drawFinal.setName("DrawFinal thread");
    }

    private boolean run;
    public void start() {
        if (!run) {
            run = true;
            try {
//                camera.start();
//                camera.yield();
//                camera.sleep(1);
                //
                drawMap.start();

                drawObj.start();
                //
                drawMap.sleep(1);

                drawObj.sleep(1);
//                camera.yield();
                //
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
//            camera.stopped();
            drawMap.stopped();
            drawObj.stopped();
            drawFinal.stopped();
        }
    }

}
