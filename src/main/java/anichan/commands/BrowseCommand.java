package anichan.commands;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command that allows the user to browse through all anime series.
 */
public class BrowseCommand extends Command {
    private int sortType;
    private int order;
    private int page;
    private int indexToPrint;
    private int animePerPage;

    //Constant values used for sortBrowseList()
    private static final int ANIME_PER_PAGE = 20;
    private static final int ID_SORT = 0;
    private static final int ORDER_DESCENDING = 0;
    private static final int NAME_SORT = 1;
    private static final int RATING_SORT = 2;
    private static final int RESET_SORT = 3;
    private static final int ORDER_ASCENDING = 1;

    //Constant Strings used for buildBrowseOutput()
    private static final String ID_HEADER = " [Id: ";
    private static final String ID_CLOSER = "]";
    private static final String ASCII_ONLY_REGEX = "[^\\x00-\\x7F]";
    private static final String INDICATE_MORE = "...";
    private static final String PERCENTAGE_STRING = "%";
    private static final String S_STRING = "s";
    private static final String EMPTY_STRING = "";
    private static final String DOT_SPACE = ". ";
    private static final String LAST_PAGE_INDICATOR = "You have reached the last page!";

    //Log Messages
    private static final String LAST_ANIME_WARNING = "Printing Last Anime Series from source";
    private static final String BROWSE_PAGE_INDICATOR = "Browsing Page: ";
    private static final String OUT_OF_BOUND_PAGE_WARNING = "Getting page: Tried to start at index: ";
    private static final String OUT_OF_BOUND_PAGE_ERROR = "Invalid Page size!";
    private static final String ASSERT_SORT_TYPE = "sortType should be < 3";
    private static final String ASSERT_ORDER_TYPE = "order should be < 2";
    private static final String SORT_ID_DESCENDING = "Sorting by ID descending";
    private static final String SORT_ID_ASCENDING = "Sorting by ID ascending";
    private static final String SORT_NAME_ASCENDING = "Sorting by Name Ascending (A to Z)";
    private static final String SORT_NAME_DESCENDING = "Sorting by Name Descending (Z to A)";
    private static final String SORT_RATING_ASCENDING = "Sorting by Rating Ascending (low to high)";
    private static final String SORT_RATING_DESCENDING = "Sorting by Rating Descending (high to low)";

    private static final Logger LOGGER = AniLogger.getAniLogger(BrowseCommand.class.getName());

    public BrowseCommand() {
        this.sortType = ID_SORT;
        this.order = ORDER_ASCENDING;
        this.page = 1;
        this.indexToPrint = 0;
        animePerPage = ANIME_PER_PAGE;
    }

    /**
     * Handles the main execution of browse command with calls to two core operations buildBrowseOutput() and
     * sortBrowseList().
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return a printable string that contains the browse output
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        ArrayList<Anime> usableList = animeData.getAnimeDataList();
        assert (sortType < 4) : ASSERT_SORT_TYPE;
        assert (order < 2) : ASSERT_ORDER_TYPE;
        sortBrowseList(usableList);
        String result = buildBrowseOutput(usableList);
        setSortType(RESET_SORT);
        sortBrowseList(usableList);
        return result;
    }

    /**
     * Called by execute to build a printable output in browse format.
     *
     * @param usableList used to retrieve the Anime object within the browse window
     * @return printable string that is formatted for browse output
     * @throws AniException if the starting index of the page supplied exceeds total number of anime
     */
    private String buildBrowseOutput(ArrayList<Anime> usableList) throws AniException {
        checkForPageBound(usableList);
        StringBuilder result = new StringBuilder();
        for (int i = indexToPrint; i < indexToPrint + animePerPage; i++) {
            Anime browseAnime = usableList.get(i);
            String animeName = browseAnime.getAnimeName();
            //Removes non-ascii and trim long titles.
            animeName = animeName.replaceAll(ASCII_ONLY_REGEX, EMPTY_STRING);
            if (animeName.length() >= 51) {
                animeName = animeName.substring(0, 48);
                animeName += INDICATE_MORE;
            }

            //Pads the output if necessary
            String currAnimeID = Integer.toString(browseAnime.getAnimeID());
            String browseIndex = i + 1 + DOT_SPACE;
            animeName = String.format(PERCENTAGE_STRING + (-51) + S_STRING, animeName.trim());
            currAnimeID = String.format(PERCENTAGE_STRING + (-3) + S_STRING, currAnimeID);
            browseIndex = String.format(PERCENTAGE_STRING + (-5) + S_STRING, browseIndex);

            result.append(browseIndex);
            result.append(animeName);
            result.append(ID_HEADER);
            result.append(currAnimeID);
            result.append(ID_CLOSER);
            result.append(System.lineSeparator());
            if (i + 1 >= usableList.size()) {
                result.append(LAST_PAGE_INDICATOR).append(System.lineSeparator());
                LOGGER.log(Level.WARNING, LAST_ANIME_WARNING);
                break;
            }
        }
        result.append(BROWSE_PAGE_INDICATOR).append(page);
        return result.toString();
    }

    /**
     * Checks if the page supplied exceed the initial starting index.
     * @param usableList the list containing Anime objects to be sorted
     * @throws AniException if the page supplied too big to be used.
     */
    private void checkForPageBound(ArrayList<Anime> usableList) throws AniException {
        if (indexToPrint >= usableList.size()) {
            LOGGER.log(Level.WARNING, OUT_OF_BOUND_PAGE_WARNING + indexToPrint);
            throw new AniException(OUT_OF_BOUND_PAGE_ERROR);
        }
    }

    /**
     * Called by execute to sort the list depending on the optional parameters supplied. May also be called to
     * reset the list back to its original state.
     *
     * @param usableList the list containing Anime objects to be sorted
     */
    private void sortBrowseList(ArrayList<Anime> usableList) {
        if (sortType == ID_SORT && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_ID_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeID).reversed());
        } else if (sortType == NAME_SORT && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_NAME_ASCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeName));
        } else if (sortType == NAME_SORT) {
            LOGGER.log(Level.INFO, SORT_NAME_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getAnimeName).reversed());
        } else if (sortType == RATING_SORT && order == ORDER_DESCENDING) {
            LOGGER.log(Level.INFO, SORT_RATING_ASCENDING);
            usableList.sort(Comparator.comparing(Anime::getRating));
        } else if (sortType == RATING_SORT) {
            LOGGER.log(Level.INFO, SORT_RATING_DESCENDING);
            usableList.sort(Comparator.comparing(Anime::getRating).reversed());
        } else if (sortType == RESET_SORT) {
            usableList.sort(Comparator.comparing(Anime::getAnimeID));
        } else {
            LOGGER.log(Level.INFO, SORT_ID_ASCENDING);
        }
    }

    /**
     * Sets the page to the supplied parameter unless it is a negative value.
     *
     * @param page the page that was requested
     */
    public void setPage(int page) {
        this.page = Math.max(page, 1);
        indexToPrint = (page - 1) * getAnimePerPage();
    }

    public int getPage() {
        return page;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getSortType() {
        return sortType;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public int getAnimePerPage() {
        return animePerPage;
    }

    public void setAnimePerPage(int animePerPage) {
        this.animePerPage = animePerPage;
    }
}
