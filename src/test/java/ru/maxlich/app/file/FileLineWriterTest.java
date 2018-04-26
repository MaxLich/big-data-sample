package ru.maxlich.app.file;

import org.junit.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;
/**
 * Created by mshulakov on 25.04.2018.
 */
public class FileLineWriterTest {

    @Test
    public void testWriteLinesGroupsForGrCount() throws Exception {
        String pathToTestFile = "output_test.txt";
        FileLineWriter fileLineWriter = new FileLineWriter(pathToTestFile);
        int testGrCount = 123456;
        fileLineWriter.writeLinesGroups(testGrCount,Collections.emptySet());

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(pathToTestFile))) {
            String str = reader.readLine();
            int givenGrCount = Integer.parseInt(str);
            assertEquals(testGrCount,givenGrCount);
        }
    }

    @Test
    public void testWriteLinesGroupsForList() throws Exception {
        String testFile = "output_test.txt";
        FileLineWriter fileLineWriter = new FileLineWriter(testFile);
        Set<List<String>> testGroupSet = new HashSet<>();
        testGroupSet.add(Arrays.asList("a;b;c","a;b;d","c;b;o"));
        testGroupSet.add(Arrays.asList("x;y;z","y;y;w"));
        testGroupSet.add(Arrays.asList("u;v;y"));
        fileLineWriter.writeLinesGroups(0, testGroupSet);

        Path pathToTestFile = Paths.get(testFile);
        List<String> listFromFile = Files.readAllLines(pathToTestFile);
        Set<List<String>> groupSetFromFile = new HashSet<>();
        List<String> currGroup = new ArrayList<>();
        for (int i = 1; i < listFromFile.size(); i++) {
            String line = listFromFile.get(i);
            if (line.startsWith("Группа")) {
                groupSetFromFile.add(currGroup);
                currGroup.clear();
            } else {
                currGroup.add(line);
            }
        }

        assertEquals(testGroupSet,groupSetFromFile);
    }
}