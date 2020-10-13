package seedu.duke.command;

import seedu.duke.anime.AnimeData;
import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.human.UserManagement;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

public class AddToWatchlistCommand extends Command {

    public AddToWatchlistCommand(String description) {
        this.description = description;
    }

    /**
     * Adds an anime to current watchlist.
     */
    @Override
    public String execute(AnimeData animeData, ArrayList<Watchlist> activeWatchlistList, Watchlist activeWatchlist,
                          UserManagement userManagement) throws AniException {
        String[] descriptionSplit = description.split(" ", 2);

        try {
            String commandOption = descriptionSplit[0];
            String animeName = descriptionSplit[1];
            if (commandOption.equals("-a") && animeName != null && !animeName.trim().isEmpty()) {
                activeWatchlist.addAnimeToList(animeName);
            } else {
                throw new AniException("addToWatchlist");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AniException("addToWatchlist");
        }

        return "Anime added to watchlist";
    }
}
