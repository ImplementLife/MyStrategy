package update.move;

public class SchemaSpeeds {
    public float maxSpeedForward;    //Максимальная скорость движения вперёд
    public float maxSpeedBack;       //Максимальная скорость движения назад
    public float acceleration;       //Ускорение скорости движения
    public float accelToBrake;       //Ускорение торможения

    public SchemaSpeeds() {
        this(100,100,100,100);
    }

    public SchemaSpeeds(float maxSpeedForward, float maxSpeedBack, float acceleration, float accelToBrake) {
        this.maxSpeedForward = maxSpeedForward;
        this.maxSpeedBack = maxSpeedBack;
        this.acceleration = acceleration;
        this.accelToBrake = accelToBrake;
    }

    public float getMaxSpeedForward() {
        return maxSpeedForward;
    }
    public void setMaxSpeedForward(float maxSpeedForward) {
        this.maxSpeedForward = maxSpeedForward;
    }

    public float getMaxSpeedBack() {
        return maxSpeedBack;
    }
    public void setMaxSpeedBack(float maxSpeedBack) {
        this.maxSpeedBack = maxSpeedBack;
    }

    public float getAcceleration() {
        return acceleration;
    }
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAccelToBrake() {
        return accelToBrake;
    }
    public void setAccelToBrake(float accelToBrake) {
        this.accelToBrake = accelToBrake;
    }
}
