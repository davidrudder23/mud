package org.noses.mud.simple.session;

import org.noses.mud.simple.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionRegistry {

    private static SessionRegistry instance;

    private HashMap<String, Session> sessions;

    private SessionRegistry() {
        sessions = new HashMap<String, Session>();
    }

    public static SessionRegistry getInstance() {
        if (instance == null) {
            instance = new SessionRegistry();
        }

        return instance;
    }

    public void register(Session session) {
        sessions.put(session.getIdentifier(), session);
    }

    public void deregister(Session session) {
        sessions.remove(session.getIdentifier());
    }

    public List<Session> getByRoom(Room room) {
        List<Session> sessionList = new ArrayList<Session>();
        for (Session session: sessions.values()) {
            if (session.getRoom().getIdentifier().equals(room.getIdentifier())) {
                sessionList.add(session);
            }
        }

        return sessionList;
    }
}
