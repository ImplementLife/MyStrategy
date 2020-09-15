package update.move;

import lib.Data;

import java.util.HashMap;
import java.util.Objects;

import static update.Updater.dt;

public abstract class SpeedController implements Mover {
    //==========     Static     =============//

    protected static class Speeds {
        private static final HashMap<Integer, Speeds> mapSpeeds = new HashMap<>();
        private static Data<Speeds> data = new Data<>();

        public final float maxSpeedForward;    //Максимальная скорость движения вперёд
        public final float maxSpeedBack;       //Максимальная скорость движения назад
        public final float acceleration;       //Ускорение скорости движения
        public final float accelToBrake;       //Ускорение торможения

        public final float distToBrakeForward; //Длинна тормозного пути при движении вперёд
        public final float distToBrakeBack;    //Длинна тормозного пути при движении назад
        public final float distAccelForward;   //Длинна пути при разгоне вперёд
        public final float distAccelBack;      //Длинна пути при разгоне назад

        private static float dist(float max, float accel) {
            float dist = 0;
            float speed = 0;
            while (speed < max) {
                speed += dt.scalar(accel);
                dist += dt.scalar(speed);
            }
            return dist;
        }
        private static float distToBrake(float speed, float accel) {
            float dist = 0;
            while (speed > 0) {
                speed -= dt.scalar(accel);
                dist += dt.scalar(speed);
            }
            return dist;
        }

        protected static Speeds getSpeeds(float maxSpeed, float maxSpeedBack, float accel, float accelToBrake) {
            Speeds speeds;
            Integer hash = Objects.hash(maxSpeed, maxSpeedBack, accel, accelToBrake);
            if (mapSpeeds.containsKey(hash)){
                speeds = mapSpeeds.get(hash);
            } else {
                speeds = new Speeds(maxSpeed, maxSpeedBack, accel, accelToBrake);
                mapSpeeds.put(hash, speeds);
            }
            return speeds;
        }

        //===   Constructor
        private Speeds(float maxSpeed, float maxSpeedBack, float accel, float accelToBrake) {
            this.maxSpeedForward = maxSpeed;
            this.maxSpeedBack = maxSpeedBack;
            this.acceleration = accel;
            this.accelToBrake = accelToBrake;

            this.distAccelForward = dist(maxSpeed, accel);
            this.distAccelBack = dist(maxSpeedBack, accel);

            this.distToBrakeForward = distToBrake(maxSpeed, accelToBrake);
            this.distToBrakeBack = distToBrake(maxSpeedBack, accelToBrake);
        }

        //===   Other methods
        private float notFullWayIntegral(double length) {
            float distAccel = 0;
            float distToBrake;
            float speed = 0;
            do {
                speed += dt.scalar(acceleration);
                distAccel += dt.scalar(speed);
                distToBrake = distToBrake(speed, accelToBrake);
            } while (!(distAccel + distToBrake > length));
            return distToBrake;
        }

        //===   Override methods
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Speeds speeds = (Speeds) o;
            return Float.compare(speeds.maxSpeedForward, maxSpeedForward) == 0 &&
                    Float.compare(speeds.maxSpeedBack, maxSpeedBack) == 0 &&
                    Float.compare(speeds.acceleration, acceleration) == 0 &&
                    Float.compare(speeds.accelToBrake, accelToBrake) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(maxSpeedForward, maxSpeedBack, acceleration, accelToBrake);
        }
    }
    //=======================================//

    protected final Speeds speeds;
    private float speed = 0f;
    private boolean moveBack, moveForward, toBrake;

    protected SpeedController(float maxSpeed, float maxSpeedBack, float accel, float accelToBrake) {
        speeds = Speeds.getSpeeds(maxSpeed, maxSpeedBack, accel, accelToBrake);
    }

    public void moveForward() {
        if (speed != 0 && !moveForward) stop();
        moveForward = true;
        moveBack = false;
    }
    public void moveBack() {
        if (speed != 0 && !moveBack) stop();
        moveBack = true;
        moveForward = false;
    }
    public boolean stop() {
        toBrake = true;
        moveBack = false;
        moveForward = false;
        return speed == 0;
    }

    private void accelerateForward() {
        if (speed < speeds.maxSpeedForward) speed += dt.scalar(speeds.acceleration);
    }
    private void accelerateBack() {
        if (Math.abs(speed) < speeds.maxSpeedBack) {
            speed -= dt.scalar(speeds.acceleration);
        }
    }
    private void toBrake() {
        if (speed - dt.scalar(speeds.accelToBrake) > 0) {
            speed -= dt.scalar(speeds.accelToBrake);
        } else if (speed + dt.scalar(speeds.accelToBrake) < 0) {
            speed += dt.scalar(speeds.accelToBrake);
        } else {
            speed = 0;
            toBrake = false;
            moveForward = false;
            moveBack = false;
        }
    }

    protected void updateSpeed() {
        if (toBrake) toBrake();
        else if (moveForward) accelerateForward();
        else if (moveBack) accelerateBack();
    }

    public float notFullWayIntegral(double length) {
        return speeds.notFullWayIntegral(length);
    }

    //==========     Getters

    public float getSpeed() {
        return speed;
    }
}