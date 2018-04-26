package ru.maxlich.app.file;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static ru.maxlich.app.file.Utils.getPathToResource;

public class FileUniqueLineReaderTest {

    @Test
    public void testReadLinesWithNotEmptyFile() throws Exception {
        Path filePath = getPathToResource("testTaskData0.csv");
        FileLineReader fileLineReader = new FileUniqueLineReader(filePath);
        assertFalse(fileLineReader.readLines().isEmpty());
    }

    @Test
    public void testReadLinesWithEmptyFile() throws Exception {
        Path filePath = getPathToResource("testTaskData_empty.csv");
        FileLineReader fileLineReader = new FileUniqueLineReader(filePath);
        assertTrue(fileLineReader.readLines().isEmpty());
    }

    @Test(expected = NoSuchFileException.class)
    public void testReadLinesWithNonExistenFile() throws IOException {
        Path filePath = Paths.get("testTaskData_not_exists.csv");
        FileLineReader fileLineReader = new FileUniqueLineReader(filePath);
        fileLineReader.readLines();
    }

    @Test(expected = java.nio.charset.MalformedInputException.class)
    public void testReadLinesWithNonTestFile() throws Exception {
        Path filePath = getPathToResource("warPluginTasks.png");
        FileLineReader fileLineReader = new FileUniqueLineReader(filePath);
        assertTrue(fileLineReader.readLines().isEmpty());
    }

    @Test
    public void testReadLinesForUniqueness() throws URISyntaxException, IOException {
        Path filePath = getPathToResource("testTaskData0.csv");
        FileLineReader fileLineReader = new FileUniqueLineReader(filePath);
        List<String> lines = fileLineReader.readLines();
        assertTrue(isLinesUnique(lines));
    }

    private boolean isLinesUnique(List<String> lines) {
        return new HashSet<>(lines).size() == lines.size();
    }

    @Test
    public void testReadLinesForListsEquivalence() throws Exception {
        String pathToTestFile = "testTaskData_test.csv";
        Set<String> testSet = new HashSet<>(Arrays.asList("a;b;c", "a;b;d", "x;y;z", "o;o;o"));
        Files.write(Paths.get(pathToTestFile), testSet,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        FileLineReader fileLineReader = new FileUniqueLineReader(pathToTestFile);
        List<String> lines = fileLineReader.readLines();

        assertEquals(new HashSet<>(lines), testSet);
    }
}