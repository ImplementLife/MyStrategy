package lib.eps;

/**
 * EPS - Execute Per Second
 */
public class EPS implements Runnable {
    public static final long TIME_NANO_PER_SECOND = 1_000_000_000; //ns

    private final Runnable exe;
    private long time;

    public EPS(Runnable exe) {
        this.exe = exe;
    }

    public long getEPS() {
        return TIME_NANO_PER_SECOND / time;
    }
    public long getTime() {
        return time;
    }

    @Override
    public void run() {
        long tempTime = System.nanoTime();
        exe.run();
        time = System.nanoTime() - tempTime;
    }
}
