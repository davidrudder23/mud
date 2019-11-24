package org.noses.mud.simple.npc;

import org.noses.mud.simple.Item;
import org.noses.mud.simple.user.Session;

public interface GiveHandler {

    public boolean handleGive(Session session, Item item);
}
