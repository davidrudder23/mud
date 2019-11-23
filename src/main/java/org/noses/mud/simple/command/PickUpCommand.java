package org.noses.mud.simple.command;

import org.noses.mud.simple.Item;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.session.Session;

public class PickUpCommand extends Command {
    public PickUpCommand() {
        names.add("pick up");
        names.add("get");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        Room room = session.getRoom();

        for (Item item: room.getItems()) {
            String itemName = item.nameMatchesLine(line);

            if (itemName != null) {
                ioHandler.sendMessage(session, "Pickup up "+itemName);
            }
        }
    }
}
