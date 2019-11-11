package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.npc.NPC;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.session.Session;

public class SayCommand extends Command {
    public SayCommand() {
        name = "say";
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        Room room = session.getRoom();

        int countPeople = 0;

        for (Session othersSession: room.getSessionsInRoom()) {
            if (othersSession.equals(session)) {
                continue;
            }

            othersSession.getIoHandler().sendMessage(session.getName()+" says \""+line.trim()+"\"");
            countPeople++;
        }

        for (NPC npc: room.getNpcs()) {
            npc.handleDialog(session, line.trim());
            countPeople++;
        }

        session.getIoHandler().sendMessage("You said \""+line+"\" "+countPeople+" times");
    }
}
