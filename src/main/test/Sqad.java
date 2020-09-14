package main.test;

import lib.math.Vec2D;

/**
 * Квадрат
 */
public class Sqad {
    private Vec2D v1, v2, v3, v4;

    private double minX, maxX, minY, maxY;

    public Sqad(Vec2D v1, Vec2D v2, Vec2D v3, Vec2D v4) {
        this.v1 = new Vec2D(v1);
        this.v2 = new Vec2D(v2);
        this.v3 = new Vec2D(v3);
        this.v4 = new Vec2D(v4);
        setMaxMin();
    }

    private void setMaxMin() {
        minX = v1.getX();
        minX = Math.min(minX, v2.getX());
        minX = Math.min(minX, v3.getX());
        minX = Math.min(minX, v4.getX());

        maxX = v1.getX();
        maxX = Math.max(maxX, v2.getX());
        maxX = Math.max(maxX, v3.getX());
        maxX = Math.max(maxX, v4.getX());

        minY = v1.getY();
        minY = Math.min(minY, v2.getY());
        minY = Math.min(minY, v3.getY());
        minY = Math.min(minY, v4.getY());

        maxY = v1.getY();
        maxY = Math.max(maxY, v2.getY());
        maxY = Math.max(maxY, v3.getY());
        maxY = Math.max(maxY, v4.getY());
    }

    public void sOut() {
        System.out.println("v1 = " + v1);
        System.out.println("v2 = " + v2);
        System.out.println("v3 = " + v3);
        System.out.println("v4 = " + v4);

        System.out.println("minX = " + minX);
        System.out.println("maxX = " + maxX);
        System.out.println("minY = " + minY);
        System.out.println("maxY = " + maxY);
    }
}
