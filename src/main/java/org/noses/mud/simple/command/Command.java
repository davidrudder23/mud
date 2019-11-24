package org.noses.mud.simple.command;

import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.user.Session;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected List<String> names = new ArrayList<String>();

    public List<String> getNames() {
        return names;
    }

    public String nameMatches(String line) {
        for (String name: names) {
            if (line.toLowerCase().startsWith(name.toLowerCase())) {
                return name;
            }
        }

        return null;
    }

    public abstract void handleCommandString(Session session, IOHandler ioHandler, String line);

    public String toString() {
        return names.get(0)+" command";
    }
}
