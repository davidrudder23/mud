package org.noses.mud.simple.command;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.session.Session;

@Slf4j
public class GoCommand extends Command {

    public GoCommand() {
        names.add("go");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        Room room = session.getRoom();

        Room exit = room.getExit(line.trim());

        if (exit == null) {
            ioHandler.sendMessage("Could not find room \""+line+"\"");
        } else {
            session.setRoom(exit);
            ioHandler.sendMessage("\n\n"+exit.getDisplay("text/plain"));
        }
    }
}
