package anichan.commands;

import anichan.anime.AnimeData;
import anichan.exception.AniException;
import anichan.human.Workspace;
import anichan.human.User;
import anichan.logger.AniLogger;
import anichan.storage.StorageManager;
import anichan.watchlist.Watchlist;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Represents the command to manage the watchlist(s) in AniChan.
 */
public class WatchlistCommand extends Command {
    private static final String CREATE_PARAM = "n";
    private static final String LIST_PARAM = "l";
    private static final String SELECT_PARAM = "s";    // Categorized as a Modification.
    private static final String DELETE_PARAM = "d";    // Categorized as a Modification.

    private static final String WATCHLIST_LIST_IS_NULL = "Watchlist list should not be null.";
    private static final String PARAMETER_IS_NULL = "Parameter should not be null.";
    private static final String INVALID_PARAMETER = "Watchlist command only accepts the parameters: "
                                                    + "-n, -l, -s, and -d.";
    private static final String WATCHLIST_NAME_IS_NOT_UNIQUE = "Watchlist name is used already!";
    private static final String EMPTY_WATCHLIST_LIST = "Uhh.. You have no watchlist..";
    private static final String INVALID_WATCHLIST_INDEX = "This is not a valid watchlist index.";
    private static final String CANNOT_SELECT_ACTIVE_WATCHLIST = "You cannot select the active watchlist..";
    private static final String CANNOT_DELETE_LAST_WATCHLIST = "You cannot delete the last watchlist!";
    private static final String WATCHLIST_NAME_TOO_LONG = "Watchlist name should not be longer than 30 characters!";

    private static final int MAX_WATCHLIST_NAME_LENGTH = 30;
    private static final Logger LOGGER = AniLogger.getAniLogger(WatchlistCommand.class.getName());

    private final String parameter;
    private final String watchlistName;
    private final int watchlistIndex;

    /**
     * Creates a new instance of WatchlistCommand the specified parameter, watchlist name, and watchlist index.
     *
     * @param parameter specified watchlist command type
     * @param watchlistName specified watchlist name (for create)
     * @param watchlistIndex specified watchlist index (for select and delete)
     */
    public WatchlistCommand(String parameter, String watchlistName, int watchlistIndex) {
        this.parameter = parameter;
        this.watchlistName = watchlistName;
        this.watchlistIndex = watchlistIndex - 1; // 1-based to 0-based numbering
    }

    /**
     * Depending on the parameter supplied, it can perform one of the following operations.
     * <ul>
     *     <li>Creates a watchlist</li>
     *     <li>List all watchlist</li>
     *     <li>Select a watchlist to be the new active watchlist</li>
     *     <li>Delete a watchlist</li>
     * </ul>
     *
     * @param animeData used to retrieve anime information
     * @param storageManager used to save or read AniChan data
     * @param user used to modify user data
     * @return result after executing the command
     * @throws AniException when an error occurred while executing the command
     */
    @Override
    public String execute(AnimeData animeData, StorageManager storageManager, User user) throws AniException {
        Workspace activeWorkspace = user.getActiveWorkspace();
        assert activeWorkspace.getWatchlistList() != null : WATCHLIST_LIST_IS_NULL;
        assert parameter != null : PARAMETER_IS_NULL;

        switch (parameter) {
        case CREATE_PARAM:
            return createWatchlist(storageManager, activeWorkspace);
        case LIST_PARAM:
            return listAllWatchlist(activeWorkspace);
        case SELECT_PARAM:
            return selectWatchlist(activeWorkspace);
        case DELETE_PARAM:
            return deleteWatchlist(storageManager, activeWorkspace);
        default:
            throw new AniException(INVALID_PARAMETER);
        }
    }

    /**
     * Creates a watchlist.
     *
     * @param storageManager used to save watchlist data
     * @param activeWorkspace used to update watchlist list and save watchlist data to correct folder
     * @return result after executing the command
     * @throws AniException when an error occurred while creating the watchlist
     */
    private String createWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        Watchlist createdWatchlist = new Watchlist(watchlistName);
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();

        boolean isWatchlistNameUnique = !watchlistList.contains(createdWatchlist);
        if (!isWatchlistNameUnique) {
            throw new AniException(WATCHLIST_NAME_IS_NOT_UNIQUE);
        }

        if (watchlistName.length() > MAX_WATCHLIST_NAME_LENGTH) {
            throw new AniException(WATCHLIST_NAME_TOO_LONG);
        }

        watchlistList.add(createdWatchlist);
        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Watchlist \"" + watchlistName + "\" created successfully.");
        return "Watchlist \"" + watchlistName + "\" has been created successfully!";
    }

    /**
     * Lists all watchlist created in the workspace in a readable format.
     *
     * @param activeWorkspace used to retrieve all watchlist created in the workspace
     * @return all watchlist created in the workspace
     */
    private String listAllWatchlist(Workspace activeWorkspace) {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        if (watchlistList.size() == 0) {
            LOGGER.log(Level.INFO, "Empty watchlistList message because size is 0");
            return EMPTY_WATCHLIST_LIST;
        }

        StringBuilder sbWatchlistList = new StringBuilder();
        sbWatchlistList.append("Currently, you have ");
        sbWatchlistList.append(watchlistList.size()).append(" watchlist(s):");
        for (int i = 0; i < watchlistList.size(); i++) {
            Watchlist watchlist = watchlistList.get(i);
            sbWatchlistList.append(System.lineSeparator());
            sbWatchlistList.append("\t").append(i + 1).append(". ");
            sbWatchlistList.append(watchlist.getName());
        }

        LOGGER.log(Level.INFO, "Listing watchlist of size: " + watchlistList.size());
        return sbWatchlistList.toString();
    }

    /**
     * Selects a watchlist to be the new active watchlist.
     *
     * @param activeWorkspace used to update the active watchlist for the active workspace
     * @return result after executing the command
     * @throws AniException when an error occurred while selecting a new active watchlist
     */
    private String selectWatchlist(Workspace activeWorkspace) throws AniException {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        validateModificationParameters(watchlistList, watchlistIndex);

        Watchlist selectedWatchlist = watchlistList.get(watchlistIndex);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        if (selectedWatchlist.equals(activeWatchlist)) {
            LOGGER.log(Level.INFO, "Select failed because the active watchlist is selected.");
            throw new AniException(CANNOT_SELECT_ACTIVE_WATCHLIST);
        }

        activeWorkspace.setActiveWatchlist(selectedWatchlist);
        LOGGER.log(Level.INFO, "New active watchlist: " + activeWorkspace.getActiveWatchlistName());
        return "\"" + selectedWatchlist.getName() + "\" is now your active watchlist!";
    }

    /**
     * Deletes a watchlist.
     *
     * @param storageManager used to save watchlist data
     * @param activeWorkspace used to update watchlist list and save watchlist data to correct folder
     * @return result after executing the command
     * @throws AniException when an error occurred while deleting a watchlist
     */
    private String deleteWatchlist(StorageManager storageManager, Workspace activeWorkspace) throws AniException {
        ArrayList<Watchlist> watchlistList = activeWorkspace.getWatchlistList();
        validateModificationParameters(watchlistList, watchlistIndex);

        Watchlist deletedWatchlist = watchlistList.get(watchlistIndex);
        Watchlist activeWatchlist = activeWorkspace.getActiveWatchlist();
        watchlistList.remove(watchlistIndex);

        String commandOutput = "Watchlist \"" + deletedWatchlist.getName() + "\" has been deleted successfully!";
        if (deletedWatchlist.equals(activeWatchlist)) {
            activeWorkspace.setActiveWatchlist(watchlistList.get(0));
            String activeWatchlistName = activeWorkspace.getActiveWatchlistName();
            commandOutput += System.lineSeparator();
            commandOutput += "Changed active watchlist to: \"" + activeWatchlistName + "\".";
        }

        storageManager.saveWatchlistList(activeWorkspace.getName(), watchlistList);
        LOGGER.log(Level.INFO, "Watchlist: \"" + deletedWatchlist.getName() + "\" deleted successfully.");
        return commandOutput;
    }

    /**
     * Validates that the parameters supplied for the select and delete watchlist command is valid.
     * <ul>
     *     <li>There is no watchlist created before.</li>
     *     <li>Attempts to delete the last watchlist.</li>
     *     <li>Watchlist index specified is out of range.</li>
     * </ul>
     *
     * @param watchlistList a list containing all of the watchlist for the active workspace
     * @param index the watchlist index to check
     * @throws AniException when the watchlist index is invalid
     */
    private void validateModificationParameters(ArrayList<Watchlist> watchlistList, int index) throws AniException {
        if (watchlistList.size() == 0) {
            throw new AniException(EMPTY_WATCHLIST_LIST);
        }

        if (watchlistList.size() == 1 && parameter.equals(DELETE_PARAM)) {
            throw new AniException(CANNOT_DELETE_LAST_WATCHLIST);
        }

        if (index < 0 || index >= watchlistList.size()) {
            throw new AniException(INVALID_WATCHLIST_INDEX);
        }
    }
}
