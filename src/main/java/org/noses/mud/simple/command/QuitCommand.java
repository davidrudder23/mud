package org.noses.mud.simple.command;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.user.Session;
import org.noses.mud.simple.user.SessionRegistry;

import java.io.IOException;

@Slf4j
public class QuitCommand extends Command {

    public QuitCommand() {
        names.add("quit");
        names.add("exit");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        ioHandler.sendMessage("Goodbye");
        try {
            SessionRegistry.getInstance().deregister(session);
            ioHandler.quit();
        } catch (IOException ioExc) {
            log.warn("Could not quit", ioExc);
        }
    }
}
