package seedu.duke.human;

import seedu.duke.bookmark.Bookmark;
import seedu.duke.exception.AniException;
import seedu.duke.watchlist.Watchlist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Workspace extends Human {
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_OTHER = "other";
    public Bookmark bookmark;

    protected Date birthdate;
    protected Gender gender;

    private Watchlist activeWatchlist;
    private ArrayList<Watchlist> watchlistList;

    public Workspace(String name, String gender) throws AniException {
        super(name);
        setGender(gender);
        bookmark = new Bookmark();
        watchlistList = new ArrayList<>();
    }

    public void setGender(String genderString) throws AniException {
        genderString = genderString.toLowerCase();

        switch (genderString) {
        case GENDER_MALE:
            gender = Gender.Male;
            break;
        case GENDER_FEMALE:
            gender = Gender.Female;
            break;
        case GENDER_OTHER:
            gender = Gender.Other;
            break;
        default:
            throw new AniException("Unexpected gender: " + genderString);
        }
    }

    public void setActiveWatchlist(Watchlist activeWatchlist) {
        this.activeWatchlist = activeWatchlist;
    }

    public void setWatchlistList(ArrayList<Watchlist> watchlistList) {
        this.watchlistList = watchlistList;
    }

    public Gender getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public Watchlist getActiveWatchlist() {
        return activeWatchlist;
    }

    public String getActiveWatchlistName() {
        return activeWatchlist.getName();
    }

    public ArrayList<Watchlist> getWatchlistList() {
        return watchlistList;
    }

    /**
     * Provides the name of the user with Japanese honorifics depending on his gender.
     *
     * @return name of user with honorifics.
     */
    public String getHonorificName() {
        if (gender == Gender.Female) {
            return name + "-chan";
        } else {
            return name + "-san";
        }
    }

    @Override
    public String toString() {
        return "\n Name: " + name + "\n Gender: " + getGender();
    }
}