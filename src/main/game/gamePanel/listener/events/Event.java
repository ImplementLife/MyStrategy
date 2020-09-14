package main.game.gamePanel.listener.events;

public class Event {
    //==========     Static     =============//
    public static final int LEFT_MOUSE_BUTTON = -1;
    public static final int RIGHT_MOUSE_BUTTON = -2;
    //=======================================//

    private State state;
    private int keyCode;

    public Event(State state, int keyCode) {
        this.state = state;
        this.keyCode = keyCode;
    }

    public State getState() {
        return state;
    }

    public boolean isPressed() {
        return state == State.PRESSED;
    }
    public boolean isReleased() {
        return state == State.RELEASED;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
