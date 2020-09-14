package main.game.gamePanel;

import main.game.gamePanel.listener.KeyListener;
import main.game.gamePanel.listener.MouseListener;
import main.game.gamePanel.listener.MouseMotionListener;
import main.game.gamePanel.listener.MouseWheelListener;

import javax.swing.*;
import java.awt.*;

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

}
