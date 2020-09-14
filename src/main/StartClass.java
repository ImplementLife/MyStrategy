package main;

import draw.game.ServiceGameDraw;
import lib.math.Vec2D;
import main.game.GameService;
import main.game.gamePanel.GamePanel;
import update.Updater;

import javax.swing.*;

public final class StartClass {
    private StartClass() {}

    private static JFrame frame;

    public static Vec2D sizeFrame() {
        return new Vec2D(frame.getSize());
    }

    public static void main(String[] args) {
        frame = new JFrame("MyStrategy 4.0");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);

        //Установка полноэкранного режима
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        //==========================================//

        GamePanel gamePanel = new GamePanel();
        frame.setContentPane(gamePanel);
        frame.setVisible(true);

        //==========================================//

        GameService gameService = GameService.getGameService();
        gameService.createMap();

        ServiceGameDraw.start(gamePanel);
        gamePanel.init();
        gameService.addUnit();
        Updater.start();



    }

    private static void test() {
        double a, b, c; //Стороны
        double angleA, angleB, angleC; //Углы напротив сторон
        // a - ?
        // b , c = ...
        //angleB = 150 deg
        b = 400; // Длинна вагона (Расстояние между тележками)
        c = 395; // Расстояние от задней тележки до поворота
        angleB = Math.toRadians(164); // Угол поворота

        double helpD = (c / b) * Math.sin(angleB);
        angleC = Math.asin(helpD);
        angleA = Math.PI - angleB - angleC;
        a = b * (Math.sin(angleA) / Math.sin(angleB));

        System.out.println("helpD = " + helpD);
        System.out.println("angleC = " + Math.toDegrees(angleC));
        System.out.println("a = " + a);
    }
    private static void test2(double ang, double length, double dir) {
        double a, b, c; //Стороны
        double angleA, angleB, angleC; //Углы напротив сторон
        // a - ?
        // b , c = ...
        //angleB = 150 deg
        b = length; // Длинна вагона (Расстояние между тележками)
        c = dir; // Расстояние от задней тележки до поворота
        angleB = ang; // Угол поворота

        double helpD = (c / b) * Math.sin(angleB);
        angleC = Math.asin(helpD);
        angleA = Math.PI - angleB - angleC;
        a = b * (Math.sin(angleA) / Math.sin(angleB));

//        System.out.println("helpD = " + helpD);

        //System.out.println("///////////////////////////////////////////////\n");
        System.out.println("result a = " + (int)a + "   ///   P = " + help(a + b + c) + "   ///   dir = " + c);

//        System.out.print("result a = " + (int)a);
//        System.out.print("   ///   b = " + b);
//        System.out.println("   ///   c = " + c + '\n');
//
//        System.out.print("angle A = " + help(Math.toDegrees(angleA)));
//        System.out.print("   ///   angle B = " + help(Math.toDegrees(angleB)));
//        System.out.print("   ///   angle C = " + help(Math.toDegrees(angleC)));
//        System.out.println("   ///   sum Angle = " + help(Math.toDegrees(angleC + angleA + angleB)));
    }
    private static void test3() {
        //        Vec2D v1 = new Vec2D(22, 16);
//        Vec2D v2 = new Vec2D(14, 26);
//        System.out.println("scalarProduct = " + Math.toDegrees(Vec2D.scalarProd(v1, v2)));

//        double length = 400;
//        double dir = 390;
//        double angle = Math.toRadians(150);
//
//        for (int i = 390; i > 0; i -= 10) {
//            test2(Math.toRadians(170), length, i);
//        }
    }
    private static double help(double d) {
        int help = (int) (d * 100);
        return help / 100;
    }

}
