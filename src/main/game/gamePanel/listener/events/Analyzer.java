package main.game.gamePanel.listener.events;

import java.util.HashSet;

public class Analyzer {
    //==========     Static     =============//
    public static final HashSet<Analyzer> list = new HashSet<>();

    public static void addEvent(Event e) {
        for (Analyzer a : list) a.putEvent(e);
    }
    //=======================================//

    private ExeEvent execute;
    public Analyzer(ExeEvent exe) {
        list.add(this);
        execute = exe;
    }

    public void setExecute(ExeEvent execute) {
        this.execute = execute;
    }

    public void putEvent(Event e) {
        execute.exe(e);
    }

    public void remove() {
        list.remove(this);
    }
}
