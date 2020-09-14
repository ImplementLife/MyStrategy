package lib;

public class Timer {

    private long timeStart, timeDelay, timeEnd;

    public Timer(long timeDelay) {
        this.timeDelay = timeDelay;
        this.timeStart = 0;
    }

    public boolean start() {
        if (timeStart == 0) {
            timeStart = System.currentTimeMillis();
            timeEnd = timeStart + timeDelay;
        }

        if (timeEnd <= System.currentTimeMillis()) {
            timeStart = 0;
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        timeStart = 0;
    }

    public void setTimeDelay(long timeDelay) {
        this.timeDelay = timeDelay;
    }
}