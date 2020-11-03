package anichan.storage;

import anichan.exception.AniException;
import anichan.human.User;
import anichan.logger.AniLogger;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Represents the class to manage user data.
 */
public class UserStorage extends Storage {
    private static final String USER_FILE_NAME = "user.txt";
    private static final String USER_LINE_DELIMITER_FOR_DECODE = " \\| ";
    private static final String USER_LINE_DELIMITER_FOR_ENCODE = " | ";

    private static final String EMPTY_USER_FILE = "Empty user file.";
    private static final String NO_USER_LOADED = "Not loaded successfully.";
    private static final String USER_DETAILS_CANNOT_BE_NULL = "User details should not have any null.";

    private static final Logger LOGGER = AniLogger.getAniLogger(UserStorage.class.getName());

    private final String storageDirectory;

    /**
     * Creates a new instance of UserStorage with the specified storage directory.
     *
     * @param storageDirectory the specified path to storage directory in hard disk
     */
    public UserStorage(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

    // ========================== Save and Load ==========================

    /**
     * Saves the user data.
     *
     * @param user the user object to be saved
     * @throws AniException when an error occurred while saving the user data
     */
    public void save(User user) throws AniException {
        String userFilePath = storageDirectory + USER_FILE_NAME;
        String encodedUserString = encode(user);

        new File(storageDirectory).mkdirs();
        writeFile(userFilePath, encodedUserString);
    }

    /**
     * Loads the user data.
     *
     * @return the user object that was loaded
     * @throws AniException when an error occurred while loading the user data
     */
    public User load() throws AniException {
        String userFilePath = storageDirectory + USER_FILE_NAME;
        String fileContent = readFile(userFilePath);
        if (fileContent.isBlank()) {
            LOGGER.log(Level.WARNING, "Empty user file: " + userFilePath);
            throw new AniException(EMPTY_USER_FILE);
        }

        String[] fileContentSplit = fileContent.split(USER_LINE_DELIMITER_FOR_DECODE, 2);
        LOGGER.log(Level.FINE, "Processing: " + System.lineSeparator() + fileContent);
        if (!isValidUserString(fileContentSplit)) {
            LOGGER.log(Level.WARNING, "Invalid user file: " + userFilePath);
            throw new AniException(NO_USER_LOADED);
        }

        return decode(fileContentSplit);
    }

    // ========================== Encode and Decode ==========================

    /**
     * Encodes the user object into a readable string representation for saving in file.
     *
     * @param user the user object to be saved
     * @return the readable string representation of the user object
     */
    private String encode(User user) {
        String userName = user.getName();
        String userGender = user.getGender().toString();

        String encodedUserString = userName + USER_LINE_DELIMITER_FOR_ENCODE + userGender;
        assert (userName != null && userGender != null) : USER_DETAILS_CANNOT_BE_NULL;
        return encodedUserString;
    }

    /**
     * Decodes the readable string representation of the user object.
     *
     * @param fileContentSplit readable string representation of the user object
     * @return the decoded user object
     * @throws AniException when an error occurs while reconstructing the user object
     */
    private User decode(String[] fileContentSplit) throws AniException {
        String userName = fileContentSplit[0].trim();
        String userGender = fileContentSplit[1].trim();
        return new User(userName, userGender);
    }

    // ========================== Validation ==========================

    /**
     * Validates the string representation read from the user data file.
     *
     * @param fileContentSplit encoded string representation of the usr object
     * @return {@code true} if the string representation is valid; {@code false} otherwise
     */
    private boolean isValidUserString(String[] fileContentSplit) {
        return (fileContentSplit.length == 2);
    }
}
