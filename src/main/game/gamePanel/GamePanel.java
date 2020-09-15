package main.game.gamePanel;

import main.game.gamePanel.listener.KeyListener;
import main.game.gamePanel.listener.MouseListener;
import main.game.gamePanel.listener.MouseMotionListener;
import main.game.gamePanel.listener.MouseWheelListener;

import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

public class GamePanel extends JPanel {

    public GamePanel() {
        super();
    }

    public Dimension getSize() {
        return super.getSize();
    }
    public void init() {
        this.setFocusable(true);
        this.requestFocus();

        this.setBackground(new Color(20,54,55));

        this.addKeyListener(new KeyListener());
        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseMotionListener());
        this.addMouseWheelListener(new MouseWheelListener());
    }


    private static TreeMap<String, Cursor> cursors;

    public void setCursor(String name) {
        if (cursors == null) cursors = new TreeMap<>();
        if (cursors.containsKey(name)) {
            setCursor(cursors.get(name));
        } else {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(name);
            Cursor cursor = toolkit.createCustomCursor(image, new Point(16, 16), "img");
            cursors.put(name, cursor);
            setCursor(cursor);
        }
    }
    public void resetCursor() {
        setCursor(Cursor.getDefaultCursor());
    }

}
