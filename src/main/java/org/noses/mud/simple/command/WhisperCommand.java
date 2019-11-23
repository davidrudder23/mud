package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.npc.NPC;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.session.Session;

public class WhisperCommand extends Command {
    public WhisperCommand() {
        names.add("whisper");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        if (line.trim().startsWith("to ")) {
            line = line.trim().substring("to ".length(), line.length());
        }
        Room room = session.getRoom();

        for (Session othersSession: room.getSessionsInRoom()) {
            if (line.startsWith(othersSession.getName()+" ")) {
                othersSession.getIoHandler().sendMessage(session.getName() + " whispers \"" + line.trim() + "\" to you");
                session.getIoHandler().sendMessage("You whispered \""+line+"\" to "+othersSession.getName());
                return;
            }
        }

        for (NPC npc: room.getNpcs()) {
            if (line.toLowerCase().startsWith(npc.getShortName().toLowerCase()+" ")) {
                line = line.substring(npc.getShortName().length()+1, line.length()).trim();
                npc.handleWhisper(session, line.trim());
                session.getIoHandler().sendMessage("You whispered \"" + line + "\" to " + npc.getShortName());
                return;
            }
        }

        session.getIoHandler().sendMessage("You could not say \""+line+"\" because we couldn't find a recipient");
    }
}
