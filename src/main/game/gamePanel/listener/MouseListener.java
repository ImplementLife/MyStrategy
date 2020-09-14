package main.game.gamePanel.listener;

import main.game.gamePanel.listener.events.Event;
import main.game.gamePanel.listener.events.State;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MouseListener extends Listener implements java.awt.event.MouseListener {

    /*============================================================*/

    @Override
    public void mouseClicked(MouseEvent e) {
//        Левая Клавиша Миши
//        if (SwingUtilities.isLeftMouseButton(e)) {}

//        Правая Клавиша Миши
//        if (SwingUtilities.isRightMouseButton(e)) {}

//        Колесо
//        if (SwingUtilities.isMiddleMouseButton(e)) {}
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Левая Клавиша Миши
        if (SwingUtilities.isLeftMouseButton(e)) {
            putEvent(new Event(State.PRESSED, Event.LEFT_MOUSE_BUTTON));
        }
        // Правая Клавиша Миши
        if (SwingUtilities.isRightMouseButton(e)) {
            putEvent(new Event(State.PRESSED, Event.RIGHT_MOUSE_BUTTON));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Левая Клавиша Миши
        if (SwingUtilities.isLeftMouseButton(e)) {
            putEvent(new Event(State.RELEASED, Event.LEFT_MOUSE_BUTTON));
        }
        // Правая Клавиша Миши
        if (SwingUtilities.isRightMouseButton(e)) {
            putEvent(new Event(State.RELEASED, Event.RIGHT_MOUSE_BUTTON));
        }

        // Колесо
        //if (SwingUtilities.isMiddleMouseButton(e)) {}
    }

    /*============================================================*/

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
