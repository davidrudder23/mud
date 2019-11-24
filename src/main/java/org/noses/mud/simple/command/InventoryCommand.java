package org.noses.mud.simple.command;

import org.noses.mud.simple.Item;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.output.ColorCodes;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.user.Session;

public class InventoryCommand extends Command {
    public InventoryCommand() {
        names.add("inventory");
    }

    @Override
    public void handleCommandString(Session session, IOHandler ioHandler, String line) {
        StringBuffer message = new StringBuffer();
        message.append(ColorCodes.color("white"));
        message.append("Inventory: ");
        message.append(ColorCodes.color("light_grey"));
        message.append("\n");

        for (Item item : session.getUser().getInventory()) {
            message.append("  ");
            message.append(item.getName());
            message.append("\n");
        }
        ioHandler.sendMessage(message.toString());
    }
}
