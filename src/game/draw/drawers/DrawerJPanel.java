package game.draw.drawers;

import javax.swing.*;
import java.awt.*;

public class DrawerJPanel {
    private JPanel panel;
    private draw.drawer.Drawer drawer;

    public DrawerJPanel(JPanel panel, draw.drawer.Drawer drawer) {
        this.panel = panel;
        this.drawer = drawer;
    }

    public void draw() {
        Graphics g2 = panel.getGraphics();
        g2.drawImage(drawer.getImage(),0,0,null);
        g2.dispose();
    }
}
