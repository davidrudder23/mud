package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.session.Session;

public class LookCommand extends Command {

    public LookCommand() {
        name = "look";
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        ioHandler.sendMessage(session.getRoom().getDisplay("text/plain"));
    }
}
