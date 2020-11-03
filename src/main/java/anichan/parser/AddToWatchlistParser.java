package anichan.parser;

import anichan.commands.AddToWatchlistCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author michaeldinata
/**
 * Handles parsing for add to watchlist command.
 */
public class AddToWatchlistParser extends CommandParser {
    protected static final String ADD_PARAM = "a";
    protected static final String SPLIT_DASH = "-";
    protected static final String NON_INTEGER_PROVIDED = "Please specify an Int value for Anime ID!";
    protected static final String TOO_MUCH_ARGUMENTS = "Add To Watchlist command " + TOO_MUCH_FIELDS;
    protected static final String OUT_OF_BOUND_INDEX_ERROR = "Anime ID is invalid!";
    private static final Logger LOGGER = AniLogger.getAniLogger(AddToWatchlistParser.class.getName());
    
    private AddToWatchlistCommand addToWatchlistCommand;

    /**
     * Creates a new instance of AddToWatchlistParser.
     */
    public AddToWatchlistParser() {
        addToWatchlistCommand = new AddToWatchlistCommand();
    }

    /**
     * Parses the specified command description.
     * 
     * @param description the specified command description
     * @return initialised {@code AddToWatchlistCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public AddToWatchlistCommand parse(String description) throws AniException {
        String[] paramGiven = description.split(SPLIT_DASH, 2);

        paramIsSetCheck(paramGiven);
        if (paramGiven[1] == null || paramGiven[1].trim().isBlank()) {
            throw new AniException(NO_PARAMETER_PROVIDED);
        }
        
        parameterParser(paramGiven[1]);
        LOGGER.log(Level.INFO, "Parameter parsed properly");

        return addToWatchlistCommand;
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param paramGiven a String Array containing the parameters and the value
     * @throws AniException when an error occurred while parsing the parameters
     */
    private void parameterParser(String paramGiven) throws AniException {

        String[] paramParts = paramGiven.split(" ");

        switch (paramParts[0].trim()) {
        case ADD_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            if (!isInteger(paramParts[1].trim())) {
                throw new AniException(NON_INTEGER_PROVIDED);
            }
            try {
                addToWatchlistCommand.setAnimeIndex(Integer.parseInt(paramParts[1].trim()));
            } catch (NumberFormatException e) {
                throw new AniException(OUT_OF_BOUND_INDEX_ERROR);
            }
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }
}
