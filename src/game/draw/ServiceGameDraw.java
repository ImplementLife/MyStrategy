package game.draw;

import draw.drawer.Drawer;
import draw.drawer.GameDrawer;
import draw.drawer.SettingsDrawer;
import draw.drawer.SettingsG;
import game.draw.camera.Camera;
import game.draw.drawers.DrawerJPanel;
import game.draw.drawers.DrawerMap;
import lib.math.Angle;
import lib.math.Vec2D;
import lib.timer.Timer;
import main.game.gamePanel.listener.events.Analyzer;
import game.objects.managers.DrawManager;

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
        size = new Vec2D(1920, 1080);

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
        }

        {
            drawerMap = new DrawerMap(painterMap);
            drawerJPanel = new DrawerJPanel(panel, finalDrawer);
        }

        {
            threadManager = ThreadManager.getThreadManager();

            threadManager.drawMap.setExe(() -> drawerMap.draw());
            threadManager.drawObj.setExe(() -> DrawManager.getDrawManager().iterate(painterObj));

            Timer timer = new Timer(500);
            threadManager.drawFinal.setExe(() -> {
                finalDrawer.fillRect(new Vec2D(), size, Color.GRAY, new Angle(0));
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
            String str = "resource/images/scr" + date();
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
