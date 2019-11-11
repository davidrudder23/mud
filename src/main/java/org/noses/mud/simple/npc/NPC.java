package org.noses.mud.simple.npc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.room.Room;
import org.noses.mud.simple.session.Session;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class NPC {

    private String shortName;
    private String longName;
    private String description;

    List<DialogListener> dialogListeners;

    Room room;

    public NPC(DialogListener dialogListener) {
        this.dialogListeners = new ArrayList<DialogListener>();
        dialogListeners.add(dialogListener);
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

}
