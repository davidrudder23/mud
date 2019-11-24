package org.noses.mud.simple;

import org.noses.mud.simple.user.Session;

public interface Giveable {

    public boolean give(Session session, Item item);
}
