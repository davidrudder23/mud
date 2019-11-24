package org.noses.mud.simple.npc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.Giveable;
import org.noses.mud.simple.Item;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.user.Session;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class NPC implements Giveable {

    private String shortName;
    private String longName;
    private String description;

    List<DialogListener> dialogListeners;

    List<GiveHandler> giveHandlers;

    Room room;

    public NPC() {
        this.dialogListeners = new ArrayList<DialogListener>();
        giveHandlers = new ArrayList<>();
    }

    public NPC(DialogListener dialogListener, GiveHandler giveHandler) {
        this();
        dialogListeners.add(dialogListener);
        giveHandlers.add(giveHandler);
    }

    public NPC(List<DialogListener> dialogListeners) {
        this.dialogListeners = dialogListeners;
    }

    public void handleDialog(Session session, String dialog) {
        for (DialogListener dialogListener: dialogListeners) {
            dialogListener.respondToTalk(session, dialog);
        }
    }

    public void handleWhisper(Session session, String dialog) {
        for (DialogListener dialogListener: dialogListeners) {
            dialogListener.respondToWhisper(session, dialog);
        }
    }

    @Override
    public boolean give(Session session, Item item) {
        for (GiveHandler giveHandler: giveHandlers) {
            if (giveHandler.handleGive(session, item)) {
                return true;
            }
        }
        return false;
    }
}
