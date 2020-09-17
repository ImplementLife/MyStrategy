package draw.game.camera;

import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;
import main.game.gamePanel.listener.events.Event;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Camera {
    private static final Vec2D mousePos = main.game.gamePanel.listener.Listener.getMousePos();
    private static final Vec2D globalMousePos = main.game.gamePanel.listener.Listener.getGlobalMousePos();
    private static final Vec2D sizeFrame = new Vec2D(1920, 1080);

    private final Analyzer analyzer = new Analyzer(e -> {
        if (e.isPressed()) {
            if (e.getKeyCode() == KeyEvent.VK_W) moveUp = true;
            if (e.getKeyCode() == KeyEvent.VK_S) moveDown = true;
            if (e.getKeyCode() == KeyEvent.VK_A) moveLeft = true;
            if (e.getKeyCode() == KeyEvent.VK_D) moveRight = true;
        } else if (e.isReleased()) {
            if (e.getKeyCode() == KeyEvent.VK_W) moveUp = false;
            if (e.getKeyCode() == KeyEvent.VK_S) moveDown = false;
            if (e.getKeyCode() == KeyEvent.VK_A) moveLeft = false;
            if (e.getKeyCode() == KeyEvent.VK_D) moveRight = false;
            if (e.getKeyCode() == KeyEvent.VK_ALT) {
                if (e.getKeyCode() == Event.RIGHT_MOUSE_BUTTON) moveTo(globalMousePos);
            }
        }
    });
    public final Vec2D firstPos;
    public final Vec2D endPos;
    public final Vec2D size;

    private final Graphics2D[] g;

    public Camera(Vec2D pos, Vec2D size, Graphics2D[] g) {
        this.firstPos = new Vec2D(pos);
        this.endPos = new Vec2D(pos).add(size);
        this.size = new Vec2D(size);
        this.g = g;
    }

    //==================================================//

    private static final float SCALE_UP = 1.1f, SCALE_DOWN = 0.90909090909090909091f;
    public float currentScale = 1.0f;
    private boolean resizeUp, resizeDown;

    public void upScale() {
        resizeUp = true;
    }
    public void downScale() {
        resizeDown = true;
    }

    /**
     * Метод служит для изменения границ отрисовки объектов
     */
    private void resize() {
        if (resizeUp) {
            resizeUp = false;
            //Масштаб увеличили
            for (Graphics2D G : g) G.scale(SCALE_UP, SCALE_UP);

            //Зону отрисовки уменьшаем
            currentScale /= SCALE_UP;

            //Расчёт новой области отрисовки
            size.setXY(1920, 1080).scalar(currentScale).ceil();
            firstPos.setXY(Vec2D.sub(globalMousePos, Vec2D.sub(globalMousePos, firstPos).antScalar(SCALE_UP)));
            endPos.setXY(Vec2D.add(firstPos, size));

            speed /= SCALE_UP;
        }
        if (resizeDown) {
            resizeDown = false;
            //Масштаб уменьшили
            for (Graphics2D G : g) G.scale(SCALE_DOWN, SCALE_DOWN);

            //Зону отрисовки увеличиваем
            currentScale /= SCALE_DOWN;

            //Расчёт новой области отрисовки
            size.setXY(1920, 1080).scalar(currentScale).ceil();
//            size.antScalar(SCALE_DOWN).ceil();
            firstPos.setXY(Vec2D.sub(globalMousePos, Vec2D.sub(globalMousePos, firstPos).antScalar(SCALE_DOWN)));
            endPos.setXY(Vec2D.add(firstPos, size));

            speed /= SCALE_DOWN;
        }
    }

    //==================================================//
    private float speed = 25;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private final MoverCamera mover = new MoverCamera();

    private void moveUp() {
        tracking(new Vec2D(0, -speed));
    }
    private void moveDown() {
        tracking(new Vec2D(0, speed));
    }
    private void moveLeft() {
        tracking(new Vec2D(-speed,0));
    }
    private void moveRight() {
        tracking(new Vec2D(speed,0));
    }

    private void moveTo(Vec2D pos) {
        mover.moveTo(pos);
    }

    public void update() {
        resize();

        mover.update();

        if (moveLeft) moveLeft();
        if (moveRight) moveRight();

        if (moveUp) moveUp();
        if (moveDown) moveDown();

        if (mousePos.getX() < 20) moveLeft();
        if (mousePos.getX() > (sizeFrame.getX() - 20)) moveRight();

        if (mousePos.getY() < 20) moveUp();
        if (mousePos.getY() > (sizeFrame.getY() - 20)) moveDown();
    }

    public void tracking(Vec2D vec) {
        firstPos.add(vec);
        endPos.add(vec);
        globalMousePos.add(vec);
    }

    class MoverCamera {
        private final Vec2D endPos;
        private final Vec2D centerPos;
        private final Vec2D course;
        private final float maxSpeed = 50f;
        private final float acceleration = 2f; //Ускорение

        private float speed = 0f;
        private float fullWayDist, dist;
        private boolean move;

        private MoverCamera() {
            this.centerPos = new Vec2D();
            this.endPos = new Vec2D();
            this.course = new Vec2D();

            int countTicksToMaxSpeed = (int) (maxSpeed / acceleration);
            float speed = 0;
            for (int i = 0; i < countTicksToMaxSpeed; i++) {
                speed += acceleration;
                fullWayDist += speed;
            }
        }

        public void moveTo(Vec2D pos) {
            endPos.setXY(pos);
            centerPos.setXY(setCenterPos());
            dist = (float) getCourse().getLength();
            if (dist < fullWayDist*2) dist /= 2;
            else dist = fullWayDist;
            move = true;
        }

        private Vec2D getCourse() {
            centerPos.setXY(setCenterPos());
            return Vec2D.sub(endPos, centerPos);
        }

        private Vec2D setCenterPos() {
//            centerPos.setXY(firstPos).sub(Vec2D.sub(firstPos, endPos).scalar(0.5));
            return Vec2D.sub(firstPos, Vec2D.sub(firstPos, endPos).scalar(0.5));
        }

        private void update() {
            centerPos.setXY(setCenterPos());
            if (move) {
                course.setXY(getCourse());
                if (course.getLength() > dist) {
                    if (speed < maxSpeed) speed += acceleration;
                } else {
                    if (speed > 0) speed -= acceleration;
                    if (speed <= 0) move = false;
                }

                tracking(new Vec2D().addAngVec(speed, course.getAngle()));
            }
        }
    }

    public Vec2D posOnCamera(Vec2D pos) {
        return Vec2D.sub(pos, firstPos);
    }
}
