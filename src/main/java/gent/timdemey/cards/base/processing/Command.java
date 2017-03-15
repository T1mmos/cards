package gent.timdemey.cards.base.processing;

import java.net.InetAddress;

import gent.timdemey.cards.base.beans.BeanUtils;

/**
 * A command is an atomic piece that unconditionally executes a specific step,
 * and has the ability to undo that step as well.
 * 
 */
public abstract class Command {

    private transient InetAddress source_ip;
    private transient int source_port;
    
    private String source_id;
    private String destination_id;

    public void setSourceIP(InetAddress source_ip) {
        this.source_ip = source_ip;
    }

    public InetAddress getSourceIP() {
        return source_ip;
    }

    public void setSourcePort(int port) {
        this.source_port = port;
    }

    public int getSourcePort() {
        return source_port;
    }

    public String getSourceID() {
        return source_id;
    }

    public String getDestinationID() {
        return destination_id;
    }

    public void setSourceID(String source_id) {
        this.source_id = source_id;
    }

    public void setDestinationID(String destination_id) {
        this.destination_id = destination_id;
    }

    public abstract void accept(Visitor visitor);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " :: " + BeanUtils.small(this);
    }
}
