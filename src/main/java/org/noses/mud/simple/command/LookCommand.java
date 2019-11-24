package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.user.Session;

public class LookCommand extends Command {

    public LookCommand() {
        names.add("look");
        names.add("see");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        ioHandler.sendMessage(session.getRoom().getDisplay("text/plain"));
    }
}
