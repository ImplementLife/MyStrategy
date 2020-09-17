package draw.game;

import draw.ThreadManager;
import draw.drawer.Drawer;
import draw.drawer.GameDrawer;
import draw.drawer.SettingsDrawer;
import draw.drawer.SettingsG;
import draw.game.camera.Camera;
import draw.game.drawers.DrawerJPanel;
import draw.game.drawers.DrawerMap;
import draw.game.drawers.DrawerObj;
import lib.timer.Timer;
import lib.math.Vec2D;
import main.game.gamePanel.listener.events.Analyzer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import static lib.myFormatString.MyFormatString.date;

public final class ServiceGameDraw {
    //==========     Static     =============//
    private static ServiceGameDraw serviceGameDraw;

    public static void start(JPanel panel) {
        if (serviceGameDraw == null) {
            serviceGameDraw = new ServiceGameDraw();
            serviceGameDraw.init(panel);
        }
    }

    public static Camera getCamera() {
        return serviceGameDraw.camera;
    }

    //=======================================//
    private ThreadManager threadManager;

    private Camera camera;

    private Drawer finalDrawer;
    private GameDrawer painterMap;
    private GameDrawer painterObj;

    private DrawerMap drawerMap;
    private DrawerObj drawerObj;
    private DrawerJPanel drawerJPanel;

    private ServiceGameDraw() {}

    private SettingsDrawer settingsDrawer;
    private void setSettingsDrawers() {
        settingsDrawer = new SettingsDrawer();
        settingsDrawer.setAntialiasing(SettingsG.QUALITY);
        settingsDrawer.setRendering(SettingsG.QUALITY);
        settingsDrawer.setDithering(SettingsG.QUALITY);
        settingsDrawer.setTextAntialiasing(SettingsG.QUALITY);
        settingsDrawer.setFractionalMetrics(SettingsG.QUALITY);
        settingsDrawer.setAlphaInterpolation(SettingsG.QUALITY);
        settingsDrawer.setColorRendering(SettingsG.QUALITY);

        settingsDrawer.setResolutionVariant(RenderingHints.VALUE_RESOLUTION_VARIANT_BASE);
        settingsDrawer.setInterpolation(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        settingsDrawer.setStrokeControl(RenderingHints.VALUE_STROKE_PURE);
    }

    private Vec2D size;
    private void init(JPanel panel) {
//        size = new Vec2D(720, 480);
//        size = new Vec2D(1280, 720);
        size = new Vec2D(1920, 1080);
//        size = new Vec2D(panel.getSize());

        {
            setSettingsDrawers();
            finalDrawer = new Drawer(size, true);
            painterMap = new GameDrawer(size, true);
            painterObj = new GameDrawer(size, true);

            finalDrawer.setAll(settingsDrawer);
            painterMap.setAll(settingsDrawer);
            painterObj.setAll(settingsDrawer);

            Graphics2D[] graphics = new Graphics2D[] {
                    painterMap.getG(), painterObj.getG()
            };
            camera = new Camera(new Vec2D(), size, graphics);
            painterObj.setSize(camera.size);
            painterMap.setSize(camera.size);
        }

        {
            drawerMap = new DrawerMap(painterMap);
            drawerObj = new DrawerObj(painterObj);
            drawerJPanel = new DrawerJPanel(panel, finalDrawer);
        }

        {
            threadManager = ThreadManager.getThreadManager();

            threadManager.drawMap.setExe(() -> drawerMap.draw());
            threadManager.drawObj.setExe(() -> drawerObj.draw());

            Timer timer = new Timer(500);
            threadManager.drawFinal.setExe(() -> {
                finalDrawer.fillRect(new Vec2D(), size, Color.GRAY);
                finalDrawer.drawImage(new Vec2D(), painterMap.getImage());
                finalDrawer.drawImage(new Vec2D(), painterObj.getImage());
                screenshot();

                if (timer.startF()) {
                    data = new String[] {
                            "UPS thread Map = " + threadManager.drawMap.getEPS(),
                            "UPS thread Obj = " + threadManager.drawObj.getEPS(),
                    };
                }

                finalDrawer.drawString(new Vec2D(), data, 16, Color.YELLOW);

                drawerJPanel.draw();

                painterMap.dispose();
                painterObj.dispose();
                camera.update();
            });
            try { Thread.sleep(1);
            } catch (InterruptedException e) { e.printStackTrace(); }
            threadManager.start();
        }
    }

    /*=======================================================*/

    private static boolean screenshot;
    private static Analyzer analyzer = new Analyzer(e -> {if(e.isReleased() && e.getKeyCode() == KeyEvent.VK_PRINTSCREEN) screenshot = true;});
    private static String data[] = new String[0];

    /*=======================================================*/

    private void screenshot() {
        if (screenshot) {
            String str = "resource/scr/" + date();
            try {
                File f = new File(str + "scr.png");
                ImageIO.write(finalDrawer.getImage(), "PNG", f);
            } catch(Exception e) {
                e.printStackTrace();
            }
            screenshot = false;
        }
    }

}
