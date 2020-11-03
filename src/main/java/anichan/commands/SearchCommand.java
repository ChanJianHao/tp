package anichan.commands;

import anichan.anime.Anime;
import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command that allows the user to search for a specific anime series.
 */
public class SearchCommand extends Command {
    private static final String ID_HEADER = "[ID:";
    private static final String ID_CLOSER = "] ";
    private static final String NO_RESULTS_FOUND = "No results found!";
    private static final String SEARCHING_BY_GENRE = "Searching By Genre";
    private static final String SEARCHING_BY_ANIME_NAME = "Searching By Anime Name";
    private static final String SEARCH_TYPE_INVALID = "Something went wrong with search input";
    private static final String SEARCH_TYPE_INVALID_LOG = "Search Type has the wrong values.";
    private static final int SEARCH_BY_NAME = 0;
    private static final int SEARCH_BY_GENRE = 1;
    private static final int NO_SEARCH_SELECTED = -1;

    private static final Logger LOGGER = AniLogger.getAniLogger(SearchCommand.class.getName());

    private String searchTerm;
    private String result;
    private String searchGenre;
    private int searchType;

    public SearchCommand() {
        searchTerm = "";
        searchGenre = "";
        result = "";
        searchType = NO_SEARCH_SELECTED;
    }

    /**
     * Handles the execution of the search and the returning of a printable result.
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return a printable string that contains the browse output
     * @throws AniException when an invalid searchType is being executed
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        switch (searchType) {
        case SEARCH_BY_NAME:
            searchForAnime(animeData);
            break;
        case SEARCH_BY_GENRE:
            searchForGenre(animeData);
            break;
        default:
            LOGGER.log(Level.SEVERE, SEARCH_TYPE_INVALID_LOG);
            throw new AniException(SEARCH_TYPE_INVALID);
        }

        if (result.isEmpty()) {
            return NO_RESULTS_FOUND;
        }
        return result;
    }

    /**
     * Finds all anime that contains the genre that is within the search term.
     *
     * @param animeData the data to search from
     */
    private void searchForGenre(AnimeData animeData) {
        LOGGER.log(Level.INFO, SEARCHING_BY_GENRE);
        StringBuilder searchOutput = new StringBuilder();
        for (Anime anime : animeData.getAnimeDataList()) {
            if (doesAnimeContainThatGenre(anime)) {
                searchOutput.append(ID_HEADER);
                searchOutput.append(anime.getAnimeID());
                searchOutput.append(ID_CLOSER);
                searchOutput.append(anime.getAnimeName());
                searchOutput.append(System.lineSeparator());
            }
        }
        result = searchOutput.toString();
    }

    /**
     * Loops through all genre that the anime has and find the search term.
     *
     * @param anime the anime to check.
     * @return true if a genre matches the search term
     */
    private boolean doesAnimeContainThatGenre(Anime anime) {
        for (String genre: anime.getGenre()) {
            if (genre.equalsIgnoreCase(searchGenre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds all anime that contains the keyword or matches the keyword exactly.
     *
     * @param animeData the data to search from
     */
    private void searchForAnime(AnimeData animeData) {
        LOGGER.log(Level.INFO, SEARCHING_BY_ANIME_NAME);
        StringBuilder searchOutput = new StringBuilder();
        for (Anime anime : animeData.getAnimeDataList()) {
            if (anime.getAnimeName().toLowerCase().contains(searchTerm)) {
                searchOutput.append(ID_HEADER);
                searchOutput.append(anime.getAnimeID());
                searchOutput.append(ID_CLOSER);
                searchOutput.append(anime.getAnimeName());
                searchOutput.append(System.lineSeparator());
            }
        }
        result = searchOutput.toString();
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm.toLowerCase();
        setSearchType(SEARCH_BY_NAME);
    }

    public void setSearchGenre(String searchGenre) {
        this.searchGenre = searchGenre.toLowerCase();
        setSearchType(SEARCH_BY_GENRE);
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public int getSearchType() {
        return searchType;
    }
}
