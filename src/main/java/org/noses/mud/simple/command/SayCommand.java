package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.npc.NPC;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.user.Session;

public class SayCommand extends Command {
    public SayCommand() {
        names.add("say");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        Room room = session.getRoom();

        int countPeople = 0;

        if (line.trim().toLowerCase().startsWith("to ")) {
            line = line.trim().substring(3, line.length());
        }

        for (Session othersSession: room.getSessionsInRoom()) {
            if (othersSession.equals(session)) {
                continue;
            }

            othersSession.getIoHandler().sendMessage(session.getUser()+" says \""+line.trim()+"\"");
            countPeople++;
        }

        for (NPC npc: room.getNpcs()) {
            npc.handleDialog(session, line.trim());
            countPeople++;
        }

        session.getIoHandler().sendMessage("You said \""+line+"\" "+countPeople+" times");
    }
}
