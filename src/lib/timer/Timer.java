package lib.timer;

public class Timer {

    protected long timeStart, timeDelay, timeEnd;

    public Timer(long timeDelay) {
        this.timeDelay = timeDelay;
        this.timeStart = 0;
    }

    public boolean startT() {
        return timeStart == 0 | startF();
    }

    public boolean startF() {
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

    public boolean startF50() {
        if (timeStart == 0) {
            timeStart = System.currentTimeMillis();
            timeEnd = timeStart + timeDelay;
        }

        if (timeStart + timeDelay/2 <= System.currentTimeMillis()) {
            if (timeStart + timeDelay <= System.currentTimeMillis()) timeStart = 0;
            return false;
        } else {
            return true;
        }
    }

    public void stop() {
        timeStart = 0;
    }

    public void setTimeDelay(long timeDelay) {
        this.timeDelay = timeDelay;
    }
}