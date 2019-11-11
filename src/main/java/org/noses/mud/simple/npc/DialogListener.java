package org.noses.mud.simple.npc;

import org.noses.mud.simple.session.Session;

public interface DialogListener {

    public void respondToTalk(Session session, String text);
    public void respondToWhisper(Session session, String text);
}
