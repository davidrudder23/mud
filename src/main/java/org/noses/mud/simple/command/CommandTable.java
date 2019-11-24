package org.noses.mud.simple.command;

import lombok.extern.slf4j.Slf4j;
import org.noses.mud.simple.input.IOHandler;
import org.noses.mud.simple.user.Session;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommandTable {

    private static CommandTable instance;

    private List<Command> commands;

    public CommandTable() {
        commands = new ArrayList<Command>();
        commands.add(new LookCommand());
        commands.add(new QuitCommand());
        commands.add(new GoCommand());
        commands.add(new SayCommand());
        commands.add(new WhisperCommand());
        commands.add(new PickUpCommand());
        commands.add(new InventoryCommand());
        commands.add(new GiveCommand());
    }

    public static CommandTable getInstance() {
        if (instance == null) {
            instance = new CommandTable();
        }
        return instance;
    }

    public void handleLine(IOHandler ioHandler, Session session, String line) {
        for (Command command: commands) {
            String matchedCommandName = command.nameMatches(line);
            if (matchedCommandName != null) {
                String stripped = line.substring(matchedCommandName.length(), line.length()).trim();
                log.info("Handling command {} with line {}", command.toString(), stripped);
                command.handleCommandString(session, ioHandler, stripped);
                return;
            }
        }

        ioHandler.sendMessage("I'm sorry, I don't understand \""+line+"\"");
        log.info("Could not handle line {}", line);
    }
}
