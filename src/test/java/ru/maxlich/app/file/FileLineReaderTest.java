package ru.maxlich.app.file;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.maxlich.app.file.Utils.getPathToResource;

/**
 * Created by mshulakov on 24.04.2018.
 */
public class FileLineReaderTest {

    @Test
    public void testReadLinesWithNotEmptyFile() throws Exception {
        Path filePath = getPathToResource("testTaskData0.csv");
        FileLineReader fileLineReader = new FileLineReader(filePath);
        assertFalse(fileLineReader.readLines().isEmpty());
    }

    @Test
    public void testReadLinesWithEmptyFile() throws Exception {
        Path filePath = getPathToResource("testTaskData_empty.csv");
        FileLineReader fileLineReader = new FileLineReader(filePath);
        assertTrue(fileLineReader.readLines().isEmpty());
    }



    @Test(expected = NoSuchFileException.class)
    public void testReadLinesWithNonExistenFile() throws IOException {
        Path filePath = Paths.get("testTaskData_not_exists.csv");
        FileLineReader fileLineReader = new FileLineReader(filePath);
        fileLineReader.readLines();
    }

    @Test(expected = java.nio.charset.MalformedInputException.class)
    public void testReadLinesWithNonTestFile() throws Exception {
        Path filePath = getPathToResource("warPluginTasks.png");
        FileLineReader fileLineReader = new FileLineReader(filePath);
        assertTrue(fileLineReader.readLines().isEmpty());
    }

    @Test
    public void testReadLinesForListsEquivalence() throws Exception {
        String pathToTestFile = "testTaskData_test.csv";
        List<String> testLines = Arrays.asList("a;b;c", "a;b;d", "x;y;z", "o;o;o");
        Files.write(Paths.get(pathToTestFile), testLines,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        FileLineReader fileLineReader = new FileLineReader(pathToTestFile);
        List<String> lines = fileLineReader.readLines();

        assertEquals(lines, testLines);
    }

   /* public static void main(String[] args) throws URISyntaxException {
        String pathToResource = "testTaskData0.csv";
        URL filePath = FileLineReaderTest
                .class
                .getClassLoader()
                .getResource(pathToResource);
        System.out.println(Paths.get(filePath.toURI()));
    }*/
}