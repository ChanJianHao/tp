package anichan.parser;

import anichan.commands.EstimateCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Handles parsing for estimate command.
 */
public class EstimateParser extends CommandParser {
    private static final String WORDS_PER_HOUR_PARAM = "-wph";
    private static final String SCRIPT_FILE_FORMAT = ".txt";
    private static final String SLASH = "/";
    private static final String DOT = ".";
    private static final String REGEX_LAST_DOT = "\\.(?=[^.]*$)";

    private static final String NO_SCRIPT_FILE_SPECIFIED = "No script file specified!";
    private static final String SPECIFIED_PATH_TO_SCRIPT_FILE = "Only specify the script file name!";
    private static final String INVALID_SCRIPT_FILE_FORMAT = "Only \".txt\" script files are accepted!";
    private static final String INVALID_PARAMETER = "Estimate command only accepts the parameter: -wph.";
    private static final String ESTIMATE_COMMAND_TOO_MUCH_FIELDS = "Estimate command" + TOO_MUCH_FIELDS;
    private static final String ESTIMATE_COMMAND_TOO_MANY_PARAMETERS = "Estimate command" + TOO_MUCH_PARAMETERS;
    private static final String NO_WORDS_PER_HOUR_SPECIFIED = "Words per hour information is missing!";
    private static final String WORDS_PER_HOUR_IS_ZERO = "Words per hour cannot be zero!";

    private static final int NO_WORDS_PER_HOUR_PROVIDED = -1;
    private static final Logger LOGGER = AniLogger.getAniLogger(EstimateParser.class.getName());

    /**
     * Parses the specified command description.
     *
     * @param description the specified command description
     * @return initialised {@code EstimateCommand} object
     * @throws AniException when an error occurred while parsing the command description
     */
    public EstimateCommand parse(String description) throws AniException {
        assert description != null : DESCRIPTION_CANNOT_BE_NULL;
        if (description.isBlank() || !description.contains(DOT)) {
            throw new AniException(NO_SCRIPT_FILE_SPECIFIED);
        }

        // paramGiven[0] would be the file name
        // paramGiven[1] would have the file extension provided (and the parameter)
        String[] paramGiven = description.trim().split(REGEX_LAST_DOT, 2);

        // secondParamGivenSplit[0] would have the file extension
        // secondParamGivenSplit[1] would not exist if there is no parameter provided
        String[] secondParamGivenSplit = paramGiven[1].split(WHITESPACE, 2);
        String fileName = paramGiven[0] + "." + secondParamGivenSplit[0];
        if (!isValidFileName(fileName)) {
            throw new AniException(INVALID_SCRIPT_FILE_FORMAT);
        }

        int wordsPerHour = NO_WORDS_PER_HOUR_PROVIDED;
        if (secondParamGivenSplit.length == 2) {
            wordsPerHour = parameterParser(secondParamGivenSplit);
        }

        LOGGER.log(Level.INFO, "Returning a EstimateCommand object with file: "
                                    + fileName + ", and wph: " + wordsPerHour + ".");
        return new EstimateCommand(fileName, wordsPerHour);
    }

    /**
     * Parses the parameter provided in the command description.
     *
     * @param paramGiven an String Array containing the parameters and the value
     * @return the parsed words per hour specified in the parameter
     * @throws AniException when an error occurred while parsing the parameters
     */
    private int parameterParser(String[] paramGiven) throws AniException {
        String[] parsedParts = paramGiven[1].trim().split(WHITESPACE, 2);
        String parameter = parsedParts[0].trim();

        // Check to ensure the correct parameter and its value is supplied and it is not just an argument
        if (!parameter.contains(DASH)) {
            throw new AniException(ESTIMATE_COMMAND_TOO_MUCH_FIELDS);
        } else if (!parameter.equals(WORDS_PER_HOUR_PARAM)) {
            throw new AniException(INVALID_PARAMETER);
        } else if (parsedParts.length == 1) {
            throw new AniException(NO_WORDS_PER_HOUR_SPECIFIED);
        }

        // Check for extra parameters or fields
        String wordsPerHourString = parsedParts[1].trim();
        if (wordsPerHourString.matches(REGEX_PARAMETER)) {
            throw new AniException(ESTIMATE_COMMAND_TOO_MANY_PARAMETERS);
        } else if (wordsPerHourString.contains(WHITESPACE)) {
            throw new AniException(ESTIMATE_COMMAND_TOO_MUCH_FIELDS);
        }

        // Integer check
        if (isNegativeInteger(wordsPerHourString)) {
            throw new AniException(NOT_POSITIVE_INTEGER);
        } else if (!isInteger(wordsPerHourString)) {
            throw new AniException(NOT_INTEGER);
        }

        int wordsPerHour = parseStringToInteger(wordsPerHourString);
        if (wordsPerHour == 0) {
            throw new AniException(WORDS_PER_HOUR_IS_ZERO);
        }

        return wordsPerHour;
    }

    /**
     * Check to ensure the user specified a valid script file.
     *
     * @param fileName script file name
     * @return {@code true} if the file name is valid; {@code false} otherwise
     * @throws AniException when the file name is invalid
     */
    private boolean isValidFileName(String fileName) throws AniException {
        if (fileName.contains(SLASH)) {
            throw new AniException(SPECIFIED_PATH_TO_SCRIPT_FILE);
        }

        String[] fileNameSplit = fileName.split(WHITESPACE);
        int numberOfTextFiles = 0;
        boolean hasAdditionalFields = false;
        for (String fileNameParts : fileNameSplit) {
            if (numberOfTextFiles == 1) {
                hasAdditionalFields = true;
            }

            if (fileNameParts.endsWith(SCRIPT_FILE_FORMAT)) {
                numberOfTextFiles++;
            }
        }

        if (hasAdditionalFields) {
            throw new AniException(ESTIMATE_COMMAND_TOO_MUCH_FIELDS);
        }

        return fileName.trim().endsWith(SCRIPT_FILE_FORMAT);
    }
}
