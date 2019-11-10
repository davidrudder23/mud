package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.session.Session;

public abstract class Command {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract void handleCommandString(Session session, IOHandler ioHandler, String line);

    public String toString() {
        return name+" command";
    }
}
