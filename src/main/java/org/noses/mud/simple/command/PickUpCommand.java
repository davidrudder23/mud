package org.noses.mud.simple.command;

import org.noses.mud.simple.Item;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.user.Session;

public class PickUpCommand extends Command {
    public PickUpCommand() {
        names.add("pick up");
        names.add("get");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        Room room = session.getRoom();

        Item itemToBePickedUp = null;

        for (Item item : room.getItems()) {
            String itemName = item.nameMatchesLine(line);

            if (itemName != null) {
                itemToBePickedUp = item;
                break;
            }
        }

        if (itemToBePickedUp != null) {
            ioHandler.sendMessage(session, "Picked up " + itemToBePickedUp.getName());
            room.getItems().remove(itemToBePickedUp);
            session.getUser().getInventory().add(itemToBePickedUp);
        } else {
            ioHandler.sendMessage("Could not pick up "+line);
        }
    }
}
