package ru.se.ifmo.s466351.lab6.server.command;

import ru.se.ifmo.s466351.lab6.server.save.SaveManager;
import ru.se.ifmo.s466351.lab6.server.collection.MovieDeque;
import ru.se.ifmo.s466351.lab6.server.user.ActiveConnection;
import ru.se.ifmo.s466351.lab6.server.user.UserCollection;

import java.util.HashMap;

public class CommandManager {
    private final MovieDeque movies;
    private final SaveManager<MovieDeque> movieSaveManager;
    private final HashMap<String, Command> commands;
    private final ActiveConnection connection;
    private final UserCollection userCollection;


    public CommandManager(MovieDeque movies,
                          SaveManager<MovieDeque> movieSaveManager,
                          ActiveConnection connection,
                          UserCollection userCollection) {
        this.movies = movies;
        this.movieSaveManager = movieSaveManager;
        this.connection = connection;
        this.userCollection = userCollection;
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
        addCommand(new SaveCommand(movies, movieSaveManager));
        addCommand(new ShowCommand(movies));
        addCommand(new SumOfOscarCountCommand(movies));
        addCommand(new UpdateCommand(movies));
        addCommand(new LoginCommand(connection, userCollection));
        addCommand(new RegisterCommand(connection, userCollection));
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
