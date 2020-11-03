package anichan.parser;

import anichan.commands.SearchCommand;
import anichan.exception.AniException;

/**
 * Handles parsing for search command.
 */
public class SearchParser extends CommandParser {
    private static final String NO_PARAM_PROVIDED = "Please provide a parameter type. Search will accept -n or -g.";
    private SearchCommand searchCommand;

    public SearchParser() {
        searchCommand = new SearchCommand();
    }

    /**
     * Parses the string parameters and creates an executable searchCommand according to the parameters.
     *
     * @param description is the parameters portion of the user input
     * @return an executable BrowseCommand object
     * @throws AniException if an error is encountered while parsing
     */
    public SearchCommand parse(String description) throws AniException {
        String[] paramGiven = description.split(DASH, FIELD_SPLIT_LIMIT);
        paramIsSetCheck(paramGiven);
        parameterParser(paramGiven);
        return searchCommand;
    }

    /**
     * Loops through each parameter and sets the option specified by each parameter.
     *
     * @param paramGiven is a String Array containing the processed parameters
     * @throws AniException if invalid parameters are parsed in
     */
    public void parameterParser(String[] paramGiven) throws AniException {
        if (paramGiven[1].isBlank()) {
            throw new AniException(NO_PARAM_PROVIDED);
        }
        String[] paramParts = paramGiven[1].split(WHITESPACE, FIELD_SPLIT_LIMIT);
        paramFieldCheck(paramParts);

        switch (paramParts[0].trim()) {
        case NAME_PARAM:
            searchCommand.setSearchTerm(paramParts[1]);
            break;
        case GENRE_PARAM:
            searchCommand.setSearchGenre(paramParts[1]);
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven[1] + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }
}
