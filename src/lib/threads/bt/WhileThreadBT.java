package lib.threads.bt;

import lib.eps.EPS;

import static lib.math.TimeConstant.TIME_MICRO_PER_SECOND;

public class WhileThreadBT extends Thread {
    private DT dt;
    private EPS eps;
    private boolean stopped, pause;

    private int countLose;
    private long timeLose;        //[ns]
//    private long timeLoseTotal;   //[ns]

    //===============================================//

    public WhileThreadBT(Runnable target) {
        init(target);
    }
    public WhileThreadBT(Runnable target, String name) {
        super(target, name);
        init(target);
    }

    private void init(Runnable target) {
        this.eps = new EPS(target);
        this.dt = new DT(0.005);
    }

    //===============================================//

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

    public DT getDt() {
        return dt;
    }

    //===============================================//

    @Override
    public void run() {
        while (!stopped) {
            if (pause) {
                synchronized (this) {
                    try { this.wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
            }
            eps.run();
            balanceTime();
        }
    }

    //===============================================//

    /**
     * Балансировка по времини
     */
    private void balanceTime() {
        long time = eps.getTime();
        if (time < dt.getValueNano()) {
            if ((time + timeLose) < dt.getValueNano()) {
                sleepUpdater(dt.getValueNano() - (time + timeLose));
                timeLose = 0;
                countLose = 0;
            } else {
                timeLose = (time + timeLose) - dt.getValueNano();
            }
        } else {
            timeLose += time - dt.getValueNano();
            countLose++;
        }
        if (countLose >= 10) {
            System.out.print("[old_dt=" + dt.getValue());
            countLose = 0;
            dt.add();
            System.out.println(" | new_dt=" + dt.getValue() + ']');
            System.out.println("------------------------------");
        }
//        timeLoseTotal += timeLose;
    }

    /**
     * Метод для балансировки по времини
     *
     * Разделяем время сна на милисекунды и наносенунды
     * а потом отправляем поток в "сон"
     */
    private void sleepUpdater(long timeNano) {
        long timeSleepNano = timeNano;
        long timeSleepMillis = 0;

        if (timeSleepNano >= TIME_MICRO_PER_SECOND) {
            timeSleepMillis = (long) Math.floor(timeSleepNano / (float)TIME_MICRO_PER_SECOND);
            timeSleepNano -= timeSleepMillis * 1_000_000;
        }

        /*========================================*/

        try {
            Thread.sleep(timeSleepMillis, (int) timeSleepNano);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
