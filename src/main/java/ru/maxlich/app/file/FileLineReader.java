package ru.maxlich.app.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by mshulakov on 24.04.2018.
 */
public class FileLineReader extends AbstractFileManager {
    public FileLineReader(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public FileLineReader(String pathToFile) {
        this.pathToFile = Paths.get(pathToFile);
    }

    public List<String> readLines() throws IOException {
        return Files.readAllLines(pathToFile);
    }

}
