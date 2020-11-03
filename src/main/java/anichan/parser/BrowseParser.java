package anichan.parser;

import anichan.commands.BrowseCommand;
import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles parsing for browse command.
 */
public class BrowseParser extends CommandParser {
    private static final String SORT_PARAM = "s";
    private static final String ORDER_PARAM = "o";
    private static final String PAGE_PARAM = "p";
    private static final String ASCENDING_FIELD = "asc";
    private static final String DESCENDING_FIELD = "dsc";
    private static final String NAME_FIELD = "name";
    private static final String RATING_FIELD = "rating";

    private static final String PARAMETER_ERROR_HEADER = "Parameter : -";
    private static final String INVALID_OPTION = " is not a valid option";
    private static final String NOT_RECOGNISED = " is not recognised!";
    private static final String BROWSE_SETTINGS_CHANGED_INFO = "Default values modified";
    private static final Logger LOGGER = AniLogger.getAniLogger(BrowseParser.class.getName());
    private static final String NO_PARAMETER_SPECIFIED_ERROR = "Seems like you did not specify a parameter type";
    private static final String INVALID_INPUT_ERROR = "This input is not accepted, please try again!";
    private static final String NO_SPACE_BETWEEN_PARAM_ERROR = "Please leave a spacing between each parameter!";
    private static final String MULTIPLE_PAGE_PARAM_ERROR = "Please specify only one -p parameter!";
    private static final String MULTIPLE_ORDER_PARAM_ERROR = "Please specify only one -o parameter!";
    private static final String MULIPLE_SORT_PARAM_ERROR = "Please specify only one -s parameter!";

    private BrowseCommand browseCommand;
    private boolean sortFlag = false;
    private boolean orderFlag = false;
    private boolean pageFlag = false;

    public BrowseParser() {
        browseCommand = new BrowseCommand();
    }

    /**
     * Parses the string parameters and creates an executable browseCommand according to the parameters.
     *
     * @param description is the parameters portion of the user input
     * @return an executable BrowseCommand object
     * @throws AniException if an error is encountered while parsing
     */
    public BrowseCommand parse(String description) throws AniException {
        checkForDashAbuse(description);
        String[] paramGiven = parameterSplitter(description);
        if (paramGiven.length > 1) {
            parameterParser(paramGiven);
            LOGGER.log(Level.INFO, BROWSE_SETTINGS_CHANGED_INFO);
        }
        return browseCommand;
    }

    /**
     * Prevents input that abuses dash splitting, such as "- - " or "---" inputs.
     *
     * @param description the raw parameter string
     * @throws AniException if dash abuse is detected
     */
    private void checkForDashAbuse(String description) throws AniException {
        //Check for invalid --- inputs
        for (int i = 0; i < description.length(); i++) {
            if (description.charAt(i) == '-') {
                if (i + 1 >= description.length() || description.charAt(i + 1) == ' ') {
                    throw new AniException(NO_PARAMETER_SPECIFIED_ERROR);
                }
                if (description.charAt(i + 1) == '-') {
                    throw new AniException(INVALID_INPUT_ERROR);
                }
            }
        }
    }

    /**
     * Loops through each parameter and sets the option specified by each parameter.
     *
     * @param paramGiven is a String Array containing the processed parameters
     * @throws AniException if invalid parameters are parsed in
     */
    private void parameterParser(String[] paramGiven) throws AniException {
        int paramLoops = 0;
        for (String param : paramGiven) {
            paramLoops++;
            //Skip first empty field which is a blank
            if (paramLoops == 1) {
                continue;
            }
            String[] paramParts = param.split(WHITESPACE, FIELD_SPLIT_LIMIT);
            switch (paramParts[0].trim()) {
            case SORT_PARAM:
                parseSortParam(paramParts);
                break;
            case ORDER_PARAM:
                checkOrderParam(paramParts);
                break;
            case PAGE_PARAM:
                parsePageParam(paramParts);
                break;
            default:
                String invalidParameter = PARAMETER_ERROR_HEADER + param + NOT_RECOGNISED;
                throw new AniException(invalidParameter);
            }
            checkForParamStacking(paramGiven.length, paramLoops, paramParts[1]);
        }
    }

    /**
     * Checks and prevents parameter stacking e.g. "-o asc-s rating" or "-s name-p 2".
     *
     * @param totalParams is the total numbber of parameters the user parsed in
     * @param paramLoops is the current parameter being handled
     * @param theParam is the parameter being handled
     * @throws AniException if parameter stacking was found
     */
    private void checkForParamStacking(int totalParams, int paramLoops, String theParam) throws AniException {
        //Forgive the last param
        if (totalParams > 2 && !theParam.endsWith(" ") && paramLoops != totalParams) {
            throw new AniException(NO_SPACE_BETWEEN_PARAM_ERROR);
        }
    }

    /**
     * Performs input validation of the page parameter and its field before setting it.
     *
     * @param paramParts the parameter and field String array
     * @throws AniException if previously page was set already.
     */
    private void parsePageParam(String[] paramParts) throws AniException {
        paramFieldCheck(paramParts);
        paramExtraFieldCheck(paramParts);
        browseCommand.setPage(parseStringToInteger(paramParts[1].trim()));
        if (pageFlag) {
            throw new AniException(MULTIPLE_PAGE_PARAM_ERROR);
        } else {
            pageFlag = true;
        }
    }

    /**
     * Performs input validation of the order parameter and its field before setting it.
     *
     * @param paramParts the field of the order parameter.
     * @throws AniException if the field is invalid
     */
    private void checkOrderParam(String[] paramParts) throws AniException {
        paramFieldCheck(paramParts);
        paramExtraFieldCheck(paramParts);
        switch (paramParts[1].trim()) {
        case ASCENDING_FIELD:
            browseCommand.setOrder(0);
            break;
        case DESCENDING_FIELD:
            browseCommand.setOrder(1);
            break;
        default:
            String paramFieldError = paramParts[1] + INVALID_OPTION;
            throw new AniException(paramFieldError);
        }
        if (orderFlag) {
            throw new AniException(MULTIPLE_ORDER_PARAM_ERROR);
        } else {
            orderFlag = true;
        }
    }

    /**
     * Performs input validation of the sortType parameter and its field before setting it.
     *
     * @param paramParts the field of the sortType parameter.
     * @throws AniException if the field is invalid
     */
    private void parseSortParam(String[] paramParts) throws AniException {
        paramFieldCheck(paramParts);
        paramExtraFieldCheck(paramParts);
        switch (paramParts[1].trim()) {
        case NAME_FIELD:
            browseCommand.setSortType(1);
            break;
        case RATING_FIELD:
            browseCommand.setSortType(2);
            break;
        default:
            String paramFieldError = paramParts[1] + INVALID_OPTION;
            throw new AniException(paramFieldError);
        }
        if (sortFlag) {
            throw new AniException(MULIPLE_SORT_PARAM_ERROR);
        } else {
            sortFlag = true;
        }
    }
}
