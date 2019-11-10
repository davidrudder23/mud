package org.noses.mud.simple.session;

import lombok.Data;
import org.noses.mud.simple.room.Room;

import java.util.UUID;

@Data
public class Session {

    private String identifier;

    private String name;

    private Room room;

    public Session() {
        this("User "+UUID.randomUUID().toString());
    }

    public Session(String identifier) {
        this.identifier = identifier;
        this.name = identifier; // TODO: replace with login
    }

}
