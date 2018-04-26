package ru.maxlich.app.file;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mshulakov on 25.04.2018.
 */
abstract class AbstractFileManager {
    protected Path pathToFile;

    // Getters and Setters
    public Path getPathToFile() {
        return pathToFile;
    }

    public String getPathToFileString() {
        return pathToFile.toAbsolutePath().toString();
    }

    public void setPathToFile(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void setPathToFileString(String pathToFile) {
        this.pathToFile = Paths.get(pathToFile);
    }
}
