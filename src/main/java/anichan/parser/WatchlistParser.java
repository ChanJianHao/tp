package anichan.parser;

import anichan.commands.WatchlistCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Handles parsing for watchlist command.
 */
public class WatchlistParser extends CommandParser {
    private static final String CREATE_PARAM = "n";
    private static final String LIST_PARAM = "l";
    private static final String SELECT_PARAM = "s";
    private static final String DELETE_PARAM = "d";
    private static final String BLANK = "";

    private static final String WATCHLIST_COMMAND_TOO_MUCH_FIELDS = "Watchlist command" + TOO_MUCH_FIELDS;
    private static final String WATCHLIST_COMMAND_TOO_MANY_PARAMETERS = "Watchlist command" + TOO_MUCH_PARAMETERS;
    private static final String WATCHLIST_NAME_IS_EMPTY = "Watchlist name cannot be empty!";
    private static final String WATCHLIST_NAME_IS_INVALID = "Watchlist name can only consist of alphanumeric "
                                                            + "characters and spaces!";
    private static final String WATCHLIST_INDEX_IS_EMPTY = "Watchlist index cannot be empty!";
    private static final String WATCHLIST_INDEX_IS_ZERO = "Watchlist index cannot be zero!";

    private static final String NO_PARAMETER_TO_CHECK = "There should be at least one element to check!";
    private static final String INVALID_PARAMETER = "Watchlist command only accepts the parameters: "
                                                    + "-n, -l, -s, and -d.";

    private static final int CREATION_REQUIRED_PARAMETER_COUNT = 2;
    private static final int LIST_REQUIRED_PARAMETER_COUNT = 1;
    private static final int MODIFICATION_REQUIRED_PARAMETER_COUNT = 2;

    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistParser.class.getName());

    /**
     * Parses the specified command description.
     *
     * @param description the specified command description
     * @return initialised {@code WatchlistCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public WatchlistCommand parse(String description) throws AniException {
        assert description != null : DESCRIPTION_CANNOT_BE_NULL;
        String[] paramGiven = description.split(DASH, 2);
        paramIsSetCheck(paramGiven);
        if (!paramGiven[0].isBlank()) {
            throw new AniException(paramGiven[0] + NOT_RECOGNISED);
        }

        String[] parsedParts = parameterParser(paramGiven);
        int watchlistIndex = 0;
        if (!parsedParts[2].equals(BLANK)) {
            watchlistIndex = parseStringToInteger(parsedParts[2]);
            if (watchlistIndex == 0) {
                throw new AniException(WATCHLIST_INDEX_IS_ZERO);
            }
        }

        LOGGER.log(Level.INFO, "Returning WatchlistCommand object with parameter: "
                                    + parsedParts[0] + ", and information: " + parsedParts[1] + ", " + parsedParts[2]);
        String parameter = parsedParts[0];
        String watchlistName = parsedParts[1];
        return new WatchlistCommand(parameter, watchlistName, watchlistIndex);
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param paramGiven an String Array containing the parameters and the value
     * @return the parsed list of parameters for initialisation
     * @throws AniException when an error while parsing the parameters
     */
    private String[] parameterParser(String[] paramGiven) throws AniException {
        if (paramGiven[1].matches(REGEX_PARAMETER)) {
            throw new AniException(WATCHLIST_COMMAND_TOO_MANY_PARAMETERS);
        } else if (paramGiven[1].startsWith(WHITESPACE)) {
            throw new AniException(INVALID_PARAMETER);
        }

        String[] parsedParts = paramGiven[1].trim().split(WHITESPACE, 2);
        String parameter = parsedParts[0].trim();
        switch (parameter) {
        case CREATE_PARAM:
            checkCreationParameters(parsedParts);
            String watchlistName = parsedParts[1].trim();
            return new String[] {parameter, watchlistName, BLANK};
        case LIST_PARAM:
            checkListParameters(parsedParts);
            return new String[] {parameter, BLANK, BLANK};
        case SELECT_PARAM:
            // Fallthrough because select parameter will call checkModificationParameters method too.
        case DELETE_PARAM:
            checkModificationParameters(parsedParts);
            String watchlistIndex = parsedParts[1].trim();
            return new String[] {parameter, BLANK, watchlistIndex};
        default:
            throw new AniException(INVALID_PARAMETER);
        }
    }

    /**
     * Validates that watchlist creation parameters are valid.
     * <ul>
     *     <li>Have the exact required parameter count.</li>
     * </ul>
     *
     * @param parsedParts the parsed parameters and the value
     * @throws AniException when the watchlist creation parameters are invalid
     */
    private void checkCreationParameters(String[] parsedParts) throws AniException {
        assert parsedParts.length != 0 : NO_PARAMETER_TO_CHECK;
        if (parsedParts.length != CREATION_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_NAME_IS_EMPTY);
        }

        if (!parsedParts[1].trim().matches(REGEX_ALPHANUMERIC)) {
            throw new AniException(WATCHLIST_NAME_IS_INVALID);
        }
    }

    /**
     * Validates that the watchlist list parameters are valid.
     * <ul>
     *     <li>Have the exact required parameter count.</li>
     * </ul>
     *
     * @param parsedParts parsed parameters and the value
     * @throws AniException when the watchlist list parameters are invalid
     */
    private void checkListParameters(String[] parsedParts) throws AniException {
        assert parsedParts.length != 0 : NO_PARAMETER_TO_CHECK;
        if (parsedParts.length > LIST_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_COMMAND_TOO_MUCH_FIELDS);
        }
    }

    /**
     * Validates that the watchlist modification (select and delete) parameters are valid.
     * <ul>
     *     <li>Have the exact required parameter count.</li>
     *     <li>Provided a parameter value that can be parsed to a positive integer.</li>
     * </ul>
     *
     * @param parsedParts the parsed parameters and the value
     * @throws AniException when the watchlist modification parameters are invalid
     */
    private void checkModificationParameters(String[] parsedParts) throws AniException {
        assert parsedParts.length != 0 : NO_PARAMETER_TO_CHECK;
        if (parsedParts.length != MODIFICATION_REQUIRED_PARAMETER_COUNT) {
            throw new AniException(WATCHLIST_INDEX_IS_EMPTY);
        }

        String watchlistIndex = parsedParts[1].trim();
        if (watchlistIndex.contains(WHITESPACE)) {
            throw new AniException(WATCHLIST_COMMAND_TOO_MUCH_FIELDS);
        }

        if (isNegativeInteger(watchlistIndex)) {
            throw new AniException(NOT_POSITIVE_INTEGER);
        }

        if (!isInteger(watchlistIndex)) {
            throw new AniException(NOT_INTEGER);
        }
    }
}
