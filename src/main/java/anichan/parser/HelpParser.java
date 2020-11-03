package anichan.parser;

import anichan.commands.HelpCommand;
import anichan.exception.AniException;

/**
 * Handles parsing for Help command.
 */
public class HelpParser extends CommandParser {
    private HelpCommand helpCommand;

    /**
     * Creates a new instance of HelpParser.
     */
    public HelpParser() {
        helpCommand = new HelpCommand();
    }

    /**
     * Parses the specified command description.
     * 
     * @param description the specified command description
     * @return initialised {@HelpCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public HelpCommand parse(String description) throws AniException {
        if (!description.isBlank()) {
            throw new AniException(DESCRIPTION_NOT_REQUIRED);
        }
        return helpCommand;
    }
}
