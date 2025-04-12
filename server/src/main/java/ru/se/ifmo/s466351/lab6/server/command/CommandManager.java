package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.common.dto.MovieDTO;
import ru.se.ifmo.s466351.lab6.server.SaveManager;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;

import java.util.HashMap;

public class CommandManager {
    private final MovieDeque movies;
    private final SaveManager saveManager;
    private final HashMap<String, Command> commands;

    public CommandManager(MovieDeque movies, SaveManager saveManager) {
        this.movies = movies;
        this.saveManager = saveManager;
        this.commands = new HashMap<>();
    }

    public void initialize() {
        addCommand(new AddCommand(movies));
        addCommand(new AddIfMaxCommand(movies));
        addCommand(new AddIfMinCommand(movies));
        addCommand(new AverageOfOscarCountCommand(movies));
        addCommand(new ClearCommand(movies));
        addCommand(new ExitCommand());
        addCommand(new GroupByGenreCommand(movies));
        addCommand(new HelpCommand(commands));
        addCommand(new InfoCommand(movies));
        addCommand(new RemoveByIdCommand(movies));
        addCommand(new RemoveIfLowerCommand(movies));
        addCommand(new SaveCommand(movies, saveManager));
        addCommand(new ShowCommand(movies));
        addCommand(new SumOfOscarCountCommand(movies));
        addCommand(new UpdateCommand(movies));
    }

    public boolean containsCommand(String command) {
        return commands.containsKey(command);
    }

    private void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }
}
