package seedu.duke.human;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;
import seedu.duke.storage.Storage;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkspaceManagementTest {
    private static final String USER_PROFILE_FILE_NAME = "userprofileTEST.txt";
    private static final String WATCHLIST_FILE_NAME = "watchlistTEST.txt";

    @Test
    void getTotalUsers_noUsers_sizeZero() {
        Storage storage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);
        UserManagement userManagement = new UserManagement(storage);
        assertEquals(0, userManagement.getTotalUsers());
    }

    @Test
    void testAddUser() throws ParseException, AniException {
        Storage testStorage = new Storage(USER_PROFILE_FILE_NAME, WATCHLIST_FILE_NAME);
        UserManagement userManagement = new UserManagement(testStorage);

        Workspace newWorkspace = userManagement.addUser("Wallace", "Male");
        assertEquals("\n Name: Wallace\n Gender: Male", newWorkspace.toString());
    }
}