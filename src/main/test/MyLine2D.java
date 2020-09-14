package main.test;

import lib.math.Vec2D;

public class MyLine2D {
    private static final double EPS = 1E-9;

    private static class Vec3D {
        double a, b, c;
        public Vec3D(Vec2D p, Vec2D q) {
            a = p.getY() - q.getY();
            b = q.getX() - p.getX();
            c = - a * p.getX() - b * p.getY();

            double z = Math.sqrt (a*a + b*b);
            if (Math.abs(z) > EPS) {
                a /= z;
                b /= z;
                c /= z;
            }
        }
        double dist (Vec2D p) {
            return a * p.getX() + b * p.getY() + c;
        }
    }

    private static class MyLine {
        Vec2D first;
        Vec2D last;
        public MyLine(Vec2D first, Vec2D last) {
            this.first = first;
            this.last = last;
        }
    }

    private static MyLine myIntersect(Vec2D a, Vec2D b, Vec2D c, Vec2D d) {
        if (!intersect(a.getX(), b.getX(), c.getX(), d.getX()) ||
            !intersect(a.getY(), b.getY(), c.getY(), d.getY())) return null;

        Vec3D m = new Vec3D(a, b);
        Vec3D n = new Vec3D(c, d);
        Vec2D left = new Vec2D();
        Vec2D right = new Vec2D();
        double zn = det(m.a, m.b, n.a, n.b);
        if (Math.abs (zn) < EPS) {
            if (Math.abs (m.dist (c)) > EPS || Math.abs (n.dist (a)) > EPS) return null;

//            left = Math.max (a, c);
//            right = Math.min (b, d);
            return new MyLine(left, right);
        }
        else {
            left.setX(-det(m.c, m.b, n.c, n.b) / zn);
            right.setX(-det (m.c, m.b, n.c, n.b) / zn);

            left.setY(-det(m.a, m.c, n.a, n.c) / zn);
            right.setY(-det(m.a, m.c, n.a, n.c) / zn);

            if (between(a.getX(), b.getX(), left.getX())
                && between(a.getY(), b.getY(), left.getY())
                && between(c.getX(), d.getX(), left.getX())
                && between(c.getY(), d.getY(), left.getY())) {
                return new MyLine(left, right);
            }
        }
        return null;
    }



    private static boolean intersect (double a, double b, double c, double d) {
        /*if (a > b) {
            double tempA = a;
            a = b;
            b = tempA;
        }
        if (c > d) {
            double tempC = c;
            c = d;
            d = tempC;
        }*/
        return Math.max(a,c) <= Math.min(b,d);
    }

    private static double det (double a, double b, double c, double d) {
        return a * d - b * c;
    }

    private static boolean between (double a, double b, double c) {
        return Math.min(a,b) <= c + EPS && c <= Math.max(a,b) + EPS;
    }

}
