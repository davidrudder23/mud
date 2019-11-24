package org.noses.mud.simple.npc;

import org.noses.mud.simple.user.Session;

public interface DialogListener {

    public void respondToTalk(Session session, String text);
    public void respondToWhisper(Session session, String text);
}
