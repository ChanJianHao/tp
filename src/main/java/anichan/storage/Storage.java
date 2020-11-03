package anichan.storage;

import anichan.exception.AniException;
import anichan.logger.AniLogger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author OngDeZhi
/**
 * Abstract class to represent the various storage type.
 */
public abstract class Storage {
    private static final String REGEX_POSITIVE_INTEGER = "^\\d+$";
    private static final String REGEX_NEGATIVE_INTEGER = "^[-]\\d+$";

    private static final String EMPTY_STRING = "";
    private static final String FILE_DOES_NOT_EXIST = "File does not exist.";
    private static final String WRITE_TO_FILE_FAILED = "Failed to write to file.";

    private static final Logger LOGGER = AniLogger.getAniLogger(Storage.class.getName());

    /**
     * Reads input file.
     *
     * @param filePath the path of the file to read from
     * @return {@code String} containing the file content
     * @throws AniException when unable to read from the file
     */
    public String readFile(String filePath) throws AniException {
        String fileContent = EMPTY_STRING;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            LOGGER.log(Level.INFO, "Read from file: " + filePath);
        } catch (IOException exception) {
            LOGGER.log(Level.INFO, "File does not exist at: " + filePath);
            throw new AniException(FILE_DOES_NOT_EXIST);
        }

        return fileContent;
    }

    /**
     * Writes to file based on the content supplied.
     *
     * @param filePath the path to the file to be written
     * @param fileContent the content to be written
     * @throws AniException When unable to write to the file
     */
    public void writeFile(String filePath, String fileContent) throws AniException {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(fileContent);
            fileWriter.close();
            LOGGER.log(Level.INFO, "Wrote to file: " + filePath);
        } catch (IOException exception) {
            LOGGER.log(Level.WARNING, "Failed to write to file at: " + filePath);
            throw new AniException(WRITE_TO_FILE_FAILED);
        }
    }

    /**
     * Checks if a {@code String} is a positive integer.
     *
     * @param integerString the {@code String} to be checked
     * @return {@code true} if {@code integerString} is a positive integer; {@code false} otherwise
     */
    public boolean isPositiveInteger(String integerString) {
        return integerString.matches(REGEX_POSITIVE_INTEGER);
    }

    /**
     * Checks if a {@code String} is a (positive or negative) integer.
     *
     * @param integerString the {@code String} to be checked
     * @return {@code true} if {@code integerString} is a (positive or negative) integer; {@code false} otherwise
     */
    public boolean isPositiveOrNegativeInteger(String integerString) {
        return integerString.matches(REGEX_POSITIVE_INTEGER) || integerString.matches(REGEX_NEGATIVE_INTEGER);
    }
}
