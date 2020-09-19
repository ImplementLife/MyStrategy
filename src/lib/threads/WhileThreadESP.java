package lib.threads;

import lib.eps.EPS;

import java.util.concurrent.Semaphore;

/**
 * EPS - Execute Per Second
 */
public class WhileThreadESP extends Thread {
    private Semaphore semaphore;
    private int weight;
    private EPS eps;
    private boolean stopped, pause;

    //=========================================//

    public WhileThreadESP(Semaphore semaphore, int weight, Runnable exe) {
        this.semaphore = semaphore;
        this.weight = weight;
        if (exe != null) this.setExe(exe);
    }
    public WhileThreadESP(Semaphore semaphore, int weight) {
        this(semaphore, weight, null);
    }

    public WhileThreadESP(Semaphore semaphore, Runnable exe) {
        this(semaphore, 1, exe);
    }
    public WhileThreadESP(Semaphore semaphore) {
        this(semaphore, 1, null);
    }

    public WhileThreadESP(Runnable exe) {
        this(null, 0, exe);
    }
    public WhileThreadESP() {

    }

    //==============================================//

    public void setExe(Runnable exe) {
        eps = new EPS(exe);
    }

    public void stopped() {
        stopped = true;
    }

    public void pause() {
        pause = true;
    }
    public void restart() {
        pause = false;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public long getEPS() {
        return eps.getEPS();
    }
    public long getTime() {
        return eps.getTime();
    }

    //===============================================//

    @Override
    public void run() {
        while (!stopped) {
            if (pause) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (semaphore != null) {
                try {
                    semaphore.acquire(weight);
//                    System.out.println(getName() + " is game.update.");
                    if (eps != null) eps.run();
                    semaphore.release(weight);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (eps != null) eps.run();
            }
        }
    }
}
