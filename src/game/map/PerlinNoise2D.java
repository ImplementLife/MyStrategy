package game.map;

import lib.math.Vec2D;

import java.util.ArrayList;

public class PerlinNoise2D {
    private static final long MAX_OCTAVES = 10;

    private static long octaves = 3;
    private static long seed;

    public static void setOctaves(long octaves) {
        if (octaves < 1) {
            PerlinNoise2D.octaves = 1;
        } else PerlinNoise2D.octaves = Math.min(octaves, MAX_OCTAVES);
    }

    /*==================================================*/
    public static ArrayList<ArrayList<Double>> getHeightMap(Vec2D size, long seed) {
        PerlinNoise2D.seed = seed;
        ArrayList<ArrayList<Double>> map = new ArrayList<>();
        for (int X = 0; X <= size.getX(); X++) {
            map.add(new ArrayList<>());
            for (int Y = 0; Y <= size.getY(); Y++) map.get(X).add(reHeight(get(X, Y)));
        }
        return map;
    }
    public static ArrayList<ArrayList<Double>> getHeightMap(Vec2D pos, Vec2D size, long seed) {
        PerlinNoise2D.seed = seed;
        Vec2D zone = Vec2D.add(pos, size);
        ArrayList<ArrayList<Double>> map = new ArrayList<>();
        for (int X = pos.getIntX(); X <= zone.getIntX(); X++) {
            map.add(new ArrayList<>());
            for (int Y = pos.getIntY(); Y <= zone.getY(); Y++) map.get(X).add(reHeight(get(X, Y)));
        }
        return map;
    }

    /*==================================================*/

    //Расчет коеффициета сглаживания через косинус
    private static double cos(double c) {
        return ((1 - Math.cos(c * Math.PI)) / 2);
    }
    /**
     * Ускоренный расчет коеффициета сглаживания
     * с помощью функции очень похожей на косинус
     */
    private static double fastCos(double c) {
        return (c * c * (3 - 2 * c));
    }

    //Функции псевдослучайного шума
    private static double valueNoise(long x, long y) {
        long n = x + y * 563;
        n = ((n + seed) << 13) ^ n;
        return (1 - ((n * (n * n * 15731 + 789221) + seed) & 0x7fffffff) / 1073741824.0);
    }
    private static double valueNoise2(long x, long y) {
        long n = x + y * 367;
        n = ((n + seed) << 11) ^ n;
        return (1 - ((n * (n * n * 20183 + 815279) + seed) & 0x7fffffff) / 1073741824.0);
    }

    //Функции интерполяции
    private static double interpolateNoise(double x, double y) {
        long longX = (long) Math.floor(x);
        long longY = (long) Math.floor(y);

        double fx = x - longX;
        double fy = y - longY;

        double cx = fastCos(fx);
        double cy = fastCos(fy);

        double interpolateA = lineInterpolate(valueNoise(longX, longY), valueNoise(longX + 1, longY), cx);
        double interpolateB = lineInterpolate(valueNoise(longX, longY + 1), valueNoise(longX + 1, longY + 1), cx);
        return lineInterpolate(interpolateA, interpolateB, cy);
    }
    private static double interpolateNoise2(double x, double y) {
        long longX = (long) Math.floor(x);
        long longY = (long) Math.floor(y);

        double fx = x - longX;
        double fy = y - longY;

        double cx = fastCos(fx);
        double cy = fastCos(fy);

        /*====================*/

        double value1 = valueNoise(longX, longY);
        double value2 = valueNoise2(longX, longY);
        double dot1 = dot(value1, value2, fx, fy);

        double value3 = valueNoise(longX + 1, longY);
        double value4 = valueNoise2(longX + 1, longY);
        double dot2 = dot(value3, value4, fx - 1, fy);

        double interpolateA = lineInterpolate(dot1, dot2, cx);

        /*--------------------*/

        double value5 = valueNoise(longX, longY + 1);
        double value6 = valueNoise2(longX, longY + 1);
        double dot3 = dot(value5, value6, fx, fy - 1);

        double value7 = valueNoise(longX + 1, longY + 1);
        double value8 = valueNoise2(longX + 1, longY + 1);
        double dot4 = dot(value7, value8, fx - 1, fy - 1);

        double interpolateB = lineInterpolate(dot3, dot4, cx);

        /*--------------------*/

        return lineInterpolate(interpolateA, interpolateB, cy);
    }

    private static double lineInterpolate(double a, double b, double c) {
        return a + c * (b - a);
    }
    private static double cosInterpolate(double a, double b, double c) {
        return lineInterpolate(a, b, fastCos(c));
    }

    //Метод для применения октав
    private static double get(double x, double y) {
        //Частота, Амплитуда, Общее значение в точке (Х)
        double frequency = 0.01, amplitude = 1, scale = 0, total = 0;

        for (long i = 0; i < octaves; i++) {
            total += interpolateNoise2(x * frequency, y * frequency) * amplitude;

            scale += amplitude;

            frequency *= 2;
            amplitude *= 0.5;
        }
        return total / scale;
    }
    private static double dot(double gx, double gy, double x, double y) {
        return gx * x + gy * y;
    }
    private static double reHeight(double height) {
        if (height <= 0.5 && height >= -0.5) return height * 1.8;
        else if (height > 0) return (height - 0.5) * 0.2 + 0.9;
        else if (height < 0) return (height + 0.5) * 0.2 - 0.9;
        return height;
    }
}
