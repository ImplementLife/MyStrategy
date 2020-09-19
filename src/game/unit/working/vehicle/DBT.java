package game.unit.working.vehicle;

/**
 * Dependence Body-Turret
 */
public class DBT {

    private final Rotate rBody;
    private final Rotate rTurret;

    private boolean bodyRotate;
    private boolean turretRotate;

    public DBT(float angleNow, float speedBody, float speedTurret) {
        rBody = new Rotate(angleNow, speedBody);
        rTurret = new Rotate(angleNow, speedTurret);
    }

    public void rotateBody(float angle) {
        rBody.setAngleEnd(angle);
        bodyRotate = true;
    }
    public void rotateTurret(float angle) {
        rTurret.setAngleEnd(angle);
        turretRotate = true;
    }

    public float getAngleBody() {
        return rBody.getAngleNow();
    }
    public float getAngleTurret() {
        return rTurret.getAngleNow();
    }

    public void update() {
        if (turretRotate) rotateTurret();
        if (bodyRotate) rotateBody();
    }

    private void rotateBody() {
        float temp = rBody.getAngleNow();
        if (rBody.rotate()) bodyRotate = false;
        rTurret.setAngleNow(rTurret.getAngleNow() + rBody.getAngleNow() - temp);
    }
    private void rotateTurret() {
        if (rTurret.rotate() && !bodyRotate) turretRotate = false;
    }

    public boolean isBodyRotate() {
        return bodyRotate;
    }
}
