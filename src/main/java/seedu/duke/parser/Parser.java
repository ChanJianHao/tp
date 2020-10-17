package seedu.duke.parser;

import seedu.duke.command.*;
import seedu.duke.exception.AniException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    /**
     * Prints the main menu of the application
     * and requests for command.
     */
    public Command getCommand(String fullCommand) throws AniException {
        LOGGER.setLevel(Level.WARNING);
        String[] fullCommandSplit = parseUserInput(fullCommand);
        String description = "";
        String command = fullCommandSplit[0];
        if (fullCommandSplit.length > 1) {
            description = fullCommandSplit[1];
        }

        switch (command) {
        case "adduser":
            return new AddWorkspaceCommand(description);
                
        case "switchuser":
            return new SwitchWorkspaceCommand(description);
 
        case "browse":
            return new BrowseCommand(description);
     
        case "watchlist":
            return new WatchlistCommand(description);
           
        case "add":
            return new AddToWatchlistCommand(description);
        
        case "remove":
            return new RemoveCommand(description);

        case "bookmark":
            return new BookmarkAnimeCommand(description);
                
        case "help":
            return new HelpCommand();

        case "exit":
            return new ExitCommand();
                
        default:
            throw new AniException("Unknown command");
        }
    }

    public static String[] parseUserInput(String input) throws AniException {
        if (input == null || input.isEmpty()) {
            LOGGER.log(Level.WARNING, "Exception occurred");
            throw new AniException("Input is empty");
        }
        LOGGER.log(Level.INFO, "Parser processing succeeded");

        String[] inputSplit = input.split(" ", 2);
        return inputSplit;
    }
}
