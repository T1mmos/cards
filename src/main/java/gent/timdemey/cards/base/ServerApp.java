package gent.timdemey.cards.base;

import java.io.IOException;

import gent.timdemey.cards.base.utils.EventLogger;

public class ServerApp {
    public static void main(String[] args) {
        EventLogger.install();
        ServerRunner runner = new ServerRunner();
        try {
            runner.start(666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
