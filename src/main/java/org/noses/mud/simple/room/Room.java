package org.noses.mud.simple.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.Item;
import org.noses.mud.simple.npc.NPC;
import org.noses.mud.simple.npc.TextDialogNPCLoader;
import org.noses.mud.simple.output.ColorCodes;
import org.noses.mud.simple.user.Session;
import org.noses.mud.simple.user.SessionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class Room {

    private String identifier;
    private String shortName;
    private String longName;
    private String description;

    private boolean isDefault = false;

    private Map<String, String> exits;

    private List<NPC> npcs = new ArrayList<NPC>();

    private List<Item> items = new ArrayList<Item>();

    @JsonProperty("npcs")
    public void setNPCs(Map<String, String> npcNameAndTypes) {
        log.info("Set NPCs called");
        for (String key: npcNameAndTypes.keySet()) {
            NPC npc = TextDialogNPCLoader.getInstance().getNPCByIdentifier(npcNameAndTypes.get(key));
            npcs.add(npc);
            log.info("{} is {}", key, npc);
        }

    }

    public String getDisplay(String dataType) {
        if (dataType.equalsIgnoreCase("text/plain")) {
            StringBuffer display = new StringBuffer();
            display.append(ColorCodes.color("white"));
            display.append(longName);
            display.append("\n");
            display.append(ColorCodes.color("light_grey"));
            display.append(description);
            display.append("\n");
            display.append("\n");

            display.append(ColorCodes.color("white"));
            display.append("People in this room: \n");
            display.append(ColorCodes.color("light_grey"));
            for (Session session: getSessionsInRoom()) {
                display.append("  ");
                display.append(session.getUser());
                display.append("\n");
            }
            display.append("\n");

            display.append(ColorCodes.color("white"));
            display.append("NPCs in this room: \n");
            display.append(ColorCodes.color("light_grey"));
            for (NPC npc: getNpcs()) {
                display.append("  ");
                display.append(npc.getShortName());
                display.append("\n");

            }
            display.append("\n");

            display.append(ColorCodes.color("white"));
            display.append("Items available to pick up:\n");
            display.append(ColorCodes.color("light_grey"));
            for (Item item: getItems()) {
                display.append("  ");
                display.append(item.getName());
                display.append("\n");
            }
            display.append("\n");

            display.append(ColorCodes.color("white"));
            display.append("Exits: ");
            display.append(ColorCodes.color("light_grey"));
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
