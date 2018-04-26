package ru.maxlich.app.file;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mshulakov on 24.04.2018.
 */
public class FileUniqueLineReader extends FileLineReader {
    public FileUniqueLineReader(Path pathToFile) {
        super(pathToFile);
    }

    public FileUniqueLineReader(String pathToFile) {
        super(pathToFile);
    }

    @Override
    public List<String> readLines() throws IOException {
        return new ArrayList<>(new HashSet<>(super.readLines()));
    }
}
