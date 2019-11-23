package org.noses.mud.simple.session;

import lombok.Data;
import org.noses.mud.simple.Item;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;

import java.util.List;
import java.util.UUID;

@Data
public class Session {

    private String identifier;

    private String name;

    private Room room;

    private IOHandler ioHandler;

    private List<Item> items;

    public Session() {
        this("User "+UUID.randomUUID().toString());
    }

    public Session(String identifier) {
        this.identifier = identifier;
        this.name = identifier; // TODO: replace with login
    }

    public boolean equals(Object other) {
        if (!(other instanceof Session)) {
            return false;
        }

        return ((Session)other).getIdentifier().equals(getIdentifier());
    }

}
