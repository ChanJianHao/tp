package anichan.parser;

import anichan.commands.BookmarkCommand;
import anichan.exception.AniException;

import anichan.logger.AniLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles parsing for bookmark command.
 */
public class BookmarkParser extends CommandParser {
    public static final String ADD_PARAM = "a";
    public static final String DELETE_PARAM = "d";
    public static final String EPISODE_PARAM = "e";
    public static final String LIST_PARAM = "l";
    public static final String INFO_PARAM = "i";
    public static final String ADD_NOTE_PARAM = "n";
    public static final String REMOVE_NOTE_PARAM = "r";
    public static final String DASH_PARAM = "-";
    private static final String PARAMETER_ERROR_HEADER = "Parameter :";
    private static final String DESCRIPTION_ERROR_HEADER = "Description :";
    private static final String BOOKMARK_LOAD_ERROR_HEADER = "Could not load bookmark command :";
    private static final String BOOKMARK_EPISODE = "edit episode";
    private static final String BOOKMARK_DELETE = "delete";
    private static final String BOOKMARK_ADD = "add";
    private static final String BOOKMARK_REMOVE_NOTE = "remove note";
    private static final String BOOKMARK_INDEX = "index";
    private static final Logger LOGGER = AniLogger.getAniLogger(BookmarkParser.class.getName());
    public static final String WHITESPACE_PARAM = " ";
    public static final String EMPTY_PARAM = "";
    public static final String BOOKMARK_INDEX_INFO_ERROR = " Bookmark index for info requires integer.";
    public static final String EXTRA_FIRST_PARAM_ERROR = " Add/Delete/List should not have extra first param.";
    public static final String BOOKMARK_LIST_EXTRA_FIELD_ERROR = " Bookmark list should not have extra field.";
    public static final String EMPTY_PARAM_ERROR = " The parameter is empty";

    private BookmarkCommand bookmarkCommand;

    public BookmarkParser() {
        bookmarkCommand = new BookmarkCommand();
    }

    /**
     * Parses the string parameters and creates an executable bookmarkCommand according to the parameters.
     * All command require 2 parameter except for bookmark info e.g bookmark bookmark_id.
     *
     * @param description is the parameters portion of the user input
     * @return and executable BookmarkCommand object
     * @throws AniException if an error is encountered while parsing
     */
    public BookmarkCommand parse(String description) throws AniException {
        String[] paramGiven = getSplitDescription(description);
        if (paramGiven.length > 1) {
            parameterParser(paramGiven[1]);
            setFirstParameter(paramGiven[0]);
        } else {
            setSingleParameter(description);
        }

        return bookmarkCommand;
    }

    /**
     * Bookmark commands only allow one dash parameter e.g "-a" or "-d".
     * The method based on the dach parameter will determine the checks to do
     * and the values to set for Bookmark Command.
     *
     * @param paramGiven is the string containing the processed parameters with field
     * @throws AniException if invalid parameters are parsed in
     */
    private void parameterParser(String paramGiven) throws AniException {
        String[] paramParts = paramGiven.split(WHITESPACE_PARAM,2);
        String bookmarkAction = paramParts[0].trim();
        paramEmptyCheck(paramGiven, paramParts);
        switch (bookmarkAction) {
        case EPISODE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(bookmarkAction);
            checkIsInteger(paramGiven, paramParts[1], BOOKMARK_EPISODE);
            bookmarkCommand.setBookmarkEpisode(parseStringToInteger(paramParts[1].trim()));
            break;
        case ADD_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(bookmarkAction);
            checkIsInteger(paramGiven, paramParts[1], BOOKMARK_ADD);
            bookmarkCommand.setAnimeIndex(parseStringToInteger(paramParts[1].trim()));
            break;
        case DELETE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(bookmarkAction);
            checkIsInteger(paramGiven, paramParts[1], BOOKMARK_DELETE);
            bookmarkCommand.setBookmarkIndex(parseStringToInteger(paramParts[1].trim()));
            break;
        case LIST_PARAM:
            bookmarkCommand.setBookmarkAction(bookmarkAction);
            listFieldCheck(paramParts);
            break;
        case ADD_NOTE_PARAM:
            paramFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(bookmarkAction);
            bookmarkCommand.setBookmarkNote(paramParts[1].trim());
            break;
        case REMOVE_NOTE_PARAM:
            paramFieldCheck(paramParts);
            paramExtraFieldCheck(paramParts);
            bookmarkCommand.setBookmarkAction(bookmarkAction);
            checkIsInteger(paramGiven, paramParts[1], BOOKMARK_REMOVE_NOTE);
            bookmarkCommand.setNoteIndex(parseStringToInteger(paramParts[1].trim()));
            break;
        default:
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED;
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Check that part of the parameter (the field) is integer.
     * Else, throw a custom error message based on the command type and the param given.
     *
     * @param paramGiven   the input with processed parameter and its field
     * @param paramPart    the field to check
     * @param bookmarkType the type of bookmark command for error message
     * @throws AniException when an error occurred while executing the command
     */
    private void checkIsInteger(String paramGiven, String paramPart, String bookmarkType) throws AniException {
        if (!isInteger(paramPart.trim())) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                    + System.lineSeparator() + " Bookmark " + bookmarkType + " param requires integer.";
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Check that the field of parameters are not empty.
     *
     * @param paramGiven the parameter with its field
     * @param paramParts array of parameter parts
     * @throws AniException if the parameter provided is empty
     */
    private void paramEmptyCheck(String paramGiven, String[] paramParts) throws AniException {
        if (paramParts.length == 0) {
            String invalidParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                    + System.lineSeparator() + EMPTY_PARAM_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidParameter);
            throw new AniException(invalidParameter);
        }
    }

    /**
     * Check that bookmark list command does not contain a field.
     *
     * @param paramParts the processed parameter
     * @throws AniException if the list field provided is not empty
     */
    private void listFieldCheck(String[] paramParts) throws AniException {
        if (paramParts.length > 1) {
            String invalidExtraField = PARAMETER_ERROR_HEADER + paramParts[1] + NOT_RECOGNISED
                    + System.lineSeparator() + BOOKMARK_LIST_EXTRA_FIELD_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidExtraField);
            throw new AniException(invalidExtraField);
        }
    }

    /**
     * Set the first parameter field, for commands that require two different variable.
     * It includes the bookmark edit episode, bookmark note and bookmark remove note command.
     *
     * @param paramGiven the first field not accompanied by parameter
     * @throws AniException if the first field is required but empty, or the first field is not required but not empty
     */
    private void setFirstParameter(String paramGiven) throws AniException {
        //Action edit(e), note(n), remove note(r) requires first parameter as bookmarkIndex
        if (bookmarkCommand.getBookmarkAction().equals(EPISODE_PARAM)
                || bookmarkCommand.getBookmarkAction().equals(ADD_NOTE_PARAM)
                || bookmarkCommand.getBookmarkAction().equals(REMOVE_NOTE_PARAM)) {
            checkIsInteger(paramGiven, paramGiven, BOOKMARK_INDEX);
            bookmarkCommand.setBookmarkIndex(parseStringToInteger(paramGiven.trim()));
        } else {
            boolean isEmpty = paramGiven.trim().equals(EMPTY_PARAM);
            if (!isEmpty) {
                String invalidFirstParameter = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                        + System.lineSeparator() + EXTRA_FIRST_PARAM_ERROR;
                LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidFirstParameter);
                throw new AniException(invalidFirstParameter);
            }
        }
    }

    /**
     * This indicate that it is a bookmark info command.
     *
     * @param paramGiven the processed parameter
     * @throws AniException if the only field is not integer or empty
     */
    private void setSingleParameter(String paramGiven) throws AniException {
        parseStringToInteger(paramGiven.trim());
        if (!isInteger(paramGiven.trim())) {
            String invalidBookmarkIndex = PARAMETER_ERROR_HEADER + paramGiven + NOT_RECOGNISED
                    + System.lineSeparator() + BOOKMARK_INDEX_INFO_ERROR;
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidBookmarkIndex);
            throw new AniException(invalidBookmarkIndex);
        }
        bookmarkCommand.setBookmarkAction(INFO_PARAM);
        bookmarkCommand.setBookmarkIndex(parseStringToInteger(paramGiven.trim()));
    }

    /**
     * Split the input provided by - to recognise the number of parameter.
     * The string array will be returned for further processing.
     *
     * @param description is the parameters portion of the user input
     * @return the individual parameter with its field
     * @throws AniException if there is more than 2 parameter provided
     */
    private String[] getSplitDescription(String description) throws AniException {
        String[] paramGiven = description.split(DASH_PARAM);
        if (paramGiven.length > 2) {
            String invalidDescription = DESCRIPTION_ERROR_HEADER + description + TOO_MUCH_FIELDS;
            LOGGER.log(Level.WARNING, BOOKMARK_LOAD_ERROR_HEADER + invalidDescription);
            throw new AniException(invalidDescription);
        }
        return paramGiven;
    }
}
