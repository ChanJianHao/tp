package anichan.parser;

import anichan.commands.HelpCommand;
import anichan.commands.Command;
import anichan.commands.ExitCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private static final Logger LOGGER = AniLogger.getAniLogger(Parser.class.getName());

    /**
     * Prints the main menu of the application
     * and requests for command.
     */
    public Command getCommand(String fullCommand) throws AniException {
        LOGGER.log(Level.INFO, "Parse: " + fullCommand);

        String[] fullCommandSplit = parseUserInput(fullCommand);
        String description = "";
        String command = fullCommandSplit[0];

        if (fullCommandSplit.length > 1) {
            description = fullCommandSplit[1];
        }

        switch (command) {
        case "workspace":
            return new WorkspaceParser().parse(description);

        case "browse":
            return new BrowseParser().parse(description);

        case "search":
            return new SearchParser().parse(description);

        case "watchlist":
            return new WatchlistParser().parse(description);
            
        case "view":
            return new ViewWatchlistParser().parse(description);

        case "add":
            return new AddToWatchlistParser().parse(description);
        
        case "remove":
            return new RemoveCommandParser().parse(description);

        case "bookmark":
            return new BookmarkParser().parse(description);

        case "estimate":
            return new EstimateParser().parse(description);
        
        case "info":
            return new InfoParser().parse(description);

        case "help":
            return new HelpParser().parse(description);

        case "exit":
            return new ExitParser().parse(description);

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
