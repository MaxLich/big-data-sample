package ru.maxlich.app.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by mshulakov on 24.04.2018.
 */
public class FileLineWriter extends AbstractFileManager {

    public FileLineWriter(Path pathToFile) {
        this.pathToFile = pathToFile;
    }

    public FileLineWriter(String pathToFile) {
        this.pathToFile = Paths.get(pathToFile);
    }

    public void writeLinesGroups(int grCount, Collection<List<String>> linesGroups) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(pathToFile,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("" + grCount + System.lineSeparator());
            int grNum = 0;

            for (List<String> group : linesGroups) {
                writer.write("Группа " + ++grNum + System.lineSeparator());
                for (String line : group) {
                    writer.write(line + System.lineSeparator());
                }
                writer.flush();
            }

            writer.flush();
        }
    }
}
