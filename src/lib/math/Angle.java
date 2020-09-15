package lib.math;

public class Angle {
    //==========     Static     =============//
    public static final float E = (float) (2 * Math.PI);

    //=======================================//

    private float value;
    public Angle(Angle angle) {
        this(angle.value);
    }
    public Angle(float value) {
        this.value = valid(value);
    }
    public Angle(double value) {
        this((float) value);
    }

    public void valid() {
        value = valid(value);
    }

    public float getValue() {
        valid();
        return value;
    }
    public void setValue(Angle angle) {
        this.value = angle.value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public void scalarValue(float scalar) {
        value *= scalar;
        valid();
    }

    public static float valid(float value) {
        while (true) {
            if (value > E) value -= E;
            else if (value < 0) value += E;
            else return value;
        }
    }
    public static float valid(double value) {
        return valid((float) value);
    }

}
