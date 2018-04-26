package ru.maxlich.app.processing;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by mshulakov on 26.04.2018.
 */
public class LineGroupsProcessorTest {
    private static List<List<String>> testGroupList;

    @BeforeClass
    public static void setUpAll() throws Exception {
        testGroupList = new ArrayList<>();
        testGroupList.add(Arrays.asList("u;v;y"));
        testGroupList.add(Arrays.asList("x;y;z", "y;y;w"));
        testGroupList.add(Arrays.asList("a;b;c", "a;b;d", "c;b;o"));
    }

    @Test
    public void testCountingLineGroups() throws Exception {
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);
        assertEquals(2, lineGroupsProcessor.sortAndCount().getGroupCount());
    }

    @Test
    public void testSortingLineGroups() throws Exception {
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);

        SortedSet<List<String>> sortedLineGroups = lineGroupsProcessor
                .sortAndCount()
                .getSortedLineGroups();

        int firstGrSize = sortedLineGroups.first().size();
        int lastGrSize = sortedLineGroups.last().size();

        assertTrue(firstGrSize >= lastGrSize);
    }

    @AfterClass
    public static void tearDownAll() throws Exception {
        testGroupList = null;
    }
}