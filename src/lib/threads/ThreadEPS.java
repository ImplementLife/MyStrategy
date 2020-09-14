package lib.threads;

import lib.eps.EPS;

/**
 * EPS - Execute Per Second
 */
public class ThreadEPS extends Thread {
    private final EPS eps;

    public ThreadEPS(Runnable exe) {
        this.eps = new EPS(exe);
    }

    public long getEPS() {
        return eps.getEPS();
    }
    public long getTime() {
        return eps.getTime();
    }

    @Override
    public void run() {
        eps.run();
    }
}
