package org.noses.mud.simple.command;

import org.noses.mud.simple.Item;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.npc.NPC;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.user.Session;

public class GiveCommand extends Command {
    public GiveCommand() {
        names.add("give");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        Room room = session.getRoom();

        line = line.trim();

        for (Item item: session.getUser().getInventory()) {
            String itemName = item.nameMatchesLine(line);

            if (itemName != null) {
                line = line.substring(itemName.length(), line.length()).trim();

                if (line.startsWith("to ")) {
                    line = line.substring(3, line.length());
                }

                for (Session otherSession: room.getSessionsInRoom()) {
                    if (otherSession.getUser().getName().equalsIgnoreCase(line)) {
                        if (otherSession.getUser().give(session, item)) {
                            session.getUser().getInventory().remove(item);
                            ioHandler.sendMessage(session, "Gave " + itemName + " to " + otherSession.getUser());
                            otherSession.getIoHandler().sendMessage(session, "Gave " + itemName + " to " + otherSession.getUser());
                            return;
                        }
                    }
                }

                for (NPC npc: room.getNpcs()) {
                    if (npc.getShortName().equalsIgnoreCase(line)) {
                        session.getUser().getInventory().remove(item);
                        npc.give(session, item);
                        return;
                    }
                }
            }
        }
    }
}
