package draw.drawer;

import lib.math.Angle;
import lib.math.Vec2D;

import java.awt.*;

public interface Draw {
    /**
     * Нарисовать контур фигуры: треугольник, квадрат, пятиугольник
     *
     * @param pos   - центральная позиция
     * @param angle - угол
     * @param color - цвет
     * @param s     - длинна стороны
     * @param c     - количество вершин ( 2 < c < 6)
     * @param t     - толщина линии
     */
    void drawShape(Vec2D pos, Angle angle, Color color, float s, int c, float t);

    /**
     * Залить фигуру: треугольник, квадрат, пятиугольник
     *
     * @param pos   - центральная позиция
     * @param angle - угол
     * @param color - цвет
     * @param s     - длинна стороны
     * @param c     - количество вершин ( 1 < c < 6)
     */
    void fillShape(Vec2D pos, Angle angle, Color color, float s, int c);

    /**
     * Нарисовать контур и залить фигуру: треугольник, квадрат, пятиугольник
     *
     * @param pos   - центральная позиция
     * @param angle - угол
     * @param colF  - цвет заливки
     * @param colD  - цвет линии контура
     * @param s     - длинна стороны
     * @param c     - количество вершин ( 2 < c < 6)
     * @param t     - толщина линии if (t <= 0) контур не рисуется
     */
    void fillShape(Vec2D pos, Angle angle, Color colF, Color colD, float s, int c, float t);


    /**
     * Нарисовать контур прямоугольника
     *
     * @param pos    - начальная позиция
     * @param size   - размер
     * @param color  - цвет
     * @param angle  - угол
     * @param t      - толщина линии
     */
    void drawRect(Vec2D pos, Vec2D size, Color color, Angle angle, float t);

    /**
     * Залить прямоугольник
     *
     * @param pos    - начальная позиция
     * @param size   - размер
     * @param color  - цвет
     * @param angle  - угол
     */
    void fillRect(Vec2D pos, Vec2D size, Color color, Angle angle);

    /**
     * Нарисовать контур и залить прямоугольник
     *
     * @param pos    - начальная позиция
     * @param size   - размер
     * @param colF   - цвет заливки
     * @param colD   - цвет линии контура
     * @param angle  - угол
     * @param t      - толщина линии if (t <= 0) контур не рисуется
     */
    void fillRect(Vec2D pos, Vec2D size, Color colF, Color colD, Angle angle, float t);


    /**
     * Нарисовать линию
     *
     * @param v1    - начало линии
     * @param v2    - конец линии
     * @param color - цвет
     * @param t     - толшина
     */
    void drawLine(Vec2D v1, Vec2D v2, Color color, float t);


    /**
     * Нарисовать контур круга
     * @param pos    - центральная позиция
     * @param radius - радиус
     * @param color  - цвет
     * @param t      - толшина
     */
    void drawCircle(Vec2D pos, float radius, Color color, float t);

    /**
     * Залить круг
     * @param pos    - центральная позиция
     * @param radius - радиус
     * @param color  - цвет
     */
    void fillCircle(Vec2D pos, float radius, Color color);

    /**
     * Нарисовать контур и залить круг
     * @param pos    - центральная позиция
     * @param radius - радиус
     * @param colF   - цвет заливки
     * @param colD   - цвет линии контура
     * @param t      - толшина
     */
    void fillCircle(Vec2D pos, float radius, Color colF, Color colD, float t);

    ///   Draw Image
    void drawImage(Vec2D pos, Image image);
    void drawImage(Vec2D pos, Image image, Angle angle);
    void drawImage(Vec2D pos, Image image, Angle angle, Vec2D offset);

    ///   Draw String
    void drawString(Vec2D pos, String text, int size, Color color);
    void drawString(Vec2D pos, String[] text, int size, Color color);
}
