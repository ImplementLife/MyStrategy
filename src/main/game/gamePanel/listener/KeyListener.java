package main.game.gamePanel.listener;

import main.game.gamePanel.listener.events.Event;
import main.game.gamePanel.listener.events.State;

import java.awt.event.KeyEvent;

public class KeyListener extends Listener implements java.awt.event.KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        putEvent(new Event(State.PRESSED, e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        putEvent(new Event(State.RELEASED, e.getKeyCode()));
    }
}
