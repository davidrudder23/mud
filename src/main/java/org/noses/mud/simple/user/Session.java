package org.noses.mud.simple.user;

import lombok.Data;
import org.noses.mud.simple.Item;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.room.Room;

import java.util.List;
import java.util.UUID;

@Data
public class Session {

    private String identifier;

    private Room room;

    private IOHandler ioHandler;

    private List<Item> items;

    private User user;

    public Session(User user) {
        this("User "+UUID.randomUUID().toString(), user);
    }

    public Session(String identifier, User user) {
        this.identifier = identifier;
        this.user = user;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Session)) {
            return false;
        }

        return ((Session)other).getIdentifier().equals(getIdentifier());
    }

    @Override
    public String toString() {
        return user.getName()+", "+user.getDescription();
    }

}
