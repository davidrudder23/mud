package org.noses.mud.simple.npc;

import lombok.Data;
import org.noses.mud.simple.user.Session;

import java.util.HashMap;
import java.util.Map;

@Data
public class TextDialogListener implements DialogListener {

    private String identifier;
    private String shortName;
    private String longName;
    private String description;

    Map<String, String> talk = new HashMap<String, String>();
    Map<String, String> whisper = new HashMap<String, String>();

    @Override
    public void respondToTalk(Session session, String text) {
        for (String talkKey: talk.keySet()) {
            if (text.startsWith(talkKey+" ") || text.equals(talkKey)) {
                session.getIoHandler().sendMessage(getShortName()+" responds: "+talk.get(talkKey));
            }
        }
    }

    @Override
    public void respondToWhisper(Session session, String text) {
        for (String whisperKey: whisper.keySet()) {
            if (text.startsWith(whisperKey+" ") || text.equals(whisperKey)) {
                session.getIoHandler().sendMessage(getShortName()+" whispers back: "+whisper.get(whisperKey));
            }
        }
    }
}
