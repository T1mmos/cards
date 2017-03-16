package gent.timdemey.cards.base.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.state.listeners.ServerListener;
import gent.timdemey.cards.base.state.listeners.StateListener;

public class EventLogger {

    static {
            System.setProperty("log4j.configurationFile", EventLogger.class.getResource("log4j2.xml").toString());
        
    }
    
    private static final Logger logger = LogManager.getLogger(EventLogger.class.getSimpleName(), new StringFormatterMessageFactory());

    public static void install() {
        
        State.INSTANCE.addStateListener(new StateListener() {

            @Override
            public void onServerRemoved(Server s) {
                logger.info("Server removed: %s (id=%s)", s.getName(), s.getServerId());
            }

            @Override
            public void onServerAdded(Server s) {
                logger.info("Server added: %s (id=%s)", s.getName(), s.getServerId());

                s.addServerListener(new ServerListener() {

                    @Override
                    public void onPlayerRemoved(Player p) {
                        logger.info("Player removed: %s (id=%s)", p.getName(), p.getId());
                    }

                    @Override
                    public void onPlayerAdded(Player p) {
                        logger.info("Player added: %s (id=%s) to server %s", p.getName(), p.getId(), s.getName());
                    }

                    @Override
                    public void onGameRemoved(Game g) {

                    }

                    @Override
                    public void onGameAdded(Game g) {

                    }
                });
            }
        });
    }
}
