package org.noses.mud.simple.room;

import lombok.Data;
import org.noses.mud.simple.session.Session;
import org.noses.mud.simple.session.SessionRegistry;

import java.util.HashMap;
import java.util.List;

@Data
public class Room {

    private String identifier;
    private String shortName;
    private String longName;
    private String description;

    private boolean isDefault = false;

    private HashMap<String, String> exits;

    public String getDisplay(String dataType) {
        if (dataType.equalsIgnoreCase("text/plain")) {
            StringBuffer display = new StringBuffer();
            display.append(longName);
            display.append("\n");
            display.append(description);
            display.append("\n");
            display.append("\n");

            display.append("People in this room: \n");
            for (Session session: getSessionsInRoom()) {
                display.append("  ");
                display.append(session.getName());
                display.append("\n");
            }
            display.append("\n");

            display.append("Rooms: ");
            display.append("\n");
            for (String exitDirection: exits.keySet()) {
                String roomId = exits.get(exitDirection);
                Room room = RoomLoader.getInstance().getRoomByIdentifier(roomId);

                if (room != null) {
                    display.append("  ");
                    display.append(exitDirection);
                    display.append(": ");
                    display.append(room.getShortName());
                }
            }
            return display.toString();
        } else {
            return "Can not display in format \""+dataType+"\"";
        }
    }

    public Room getExit(String direction) {
        String roomId = exits.get(direction);
        if (roomId == null) {
            return null;
        }

        return RoomLoader.getInstance().getRoomByIdentifier(roomId);
    }

    public List<Session> getSessionsInRoom() {
        return SessionRegistry.getInstance().getByRoom(this);
    }
}
