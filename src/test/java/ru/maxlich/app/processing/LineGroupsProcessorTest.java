package ru.maxlich.app.processing;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by mshulakov on 26.04.2018.
 */
public class LineGroupsProcessorTest {
    @Test
    public void testCountingLineGroups() throws Exception {
        List<List<String>> testGroupList = new ArrayList<>();
        testGroupList.add(Arrays.asList("u;v;y"));
        testGroupList.add(Arrays.asList("a;b;c", "a;b;d", "c;b;o"));
        testGroupList.add(Arrays.asList("x;y;z", "y;y;w"));
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);
        int expectedGrCount = 2;
        assertEquals(expectedGrCount, lineGroupsProcessor.sortAndCount().getGroupCount());
    }

    @Test
    public void testSortingLineGroups() throws Exception {
        List<List<String>> testGroupList = new ArrayList<>();
        testGroupList.add(Arrays.asList("u;v;y"));
        testGroupList.add(Arrays.asList("x;y;z", "y;y;w"));
        testGroupList.add(Arrays.asList("a;b;c", "a;b;d", "c;b;o"));
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);

        SortedSet<List<String>> sortedLineGroups = lineGroupsProcessor
                .sortAndCount()
                .getSortedLineGroups();

        assertNotNull(sortedLineGroups);
        assertFalse(sortedLineGroups.isEmpty());

        int firstGrSize = sortedLineGroups.first().size();
        int lastGrSize = sortedLineGroups.last().size();

        assertTrue(firstGrSize >= lastGrSize);
    }


    @Test
    public void testSortAndCountWithNullLineGroupList() {
        List<List<String>> testGroupList = null;
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);
        lineGroupsProcessor.sortAndCount();

        int expectedGrCount = 0;
        assertEquals(expectedGrCount, lineGroupsProcessor.getGroupCount());

        SortedSet<List<String>> sortedLineGroups = lineGroupsProcessor.getSortedLineGroups();
        assertNotNull(sortedLineGroups);
        assertTrue(sortedLineGroups.isEmpty());
    }
    @Test
    public void testSortAndCountWithEmptyLineGroupList() {
        List<List<String>> testGroupList = Collections.emptyList();
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);
        lineGroupsProcessor.sortAndCount();

        int expectedGrCount = 0;
        assertEquals(expectedGrCount, lineGroupsProcessor.getGroupCount());

        SortedSet<List<String>> sortedLineGroups = lineGroupsProcessor.getSortedLineGroups();
        assertNotNull(sortedLineGroups);
        assertTrue(sortedLineGroups.isEmpty());
    }
    @Test
    public void testSortAndCountWithSingleLineGroupList() {
        List<List<String>> testGroupList = new ArrayList<>();
        testGroupList.add(Collections.singletonList("u;v;y"));
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);
        lineGroupsProcessor.sortAndCount();

        int expectedGrCount = 0;
        assertEquals(expectedGrCount, lineGroupsProcessor.getGroupCount());

        SortedSet<List<String>> sortedLineGroups = lineGroupsProcessor.getSortedLineGroups();
        assertNotNull(sortedLineGroups);
        int expectedTotalGrCount = 1;
        assertEquals(expectedTotalGrCount, sortedLineGroups.size());

        List<String> firstGroupFromSortedSet = sortedLineGroups.first();
        List<String> firstGroupFromSourceGroupList = testGroupList.get(0);
        assertEquals(firstGroupFromSortedSet,firstGroupFromSourceGroupList);
    }

    @Test
    public void testSortAndCountWithLineGroupListWithTwoLinesGroup() {
        List<List<String>> testGroupList = new ArrayList<>();
        testGroupList.add(Arrays.asList("x;y;z", "y;y;w"));
        LineGroupsProcessor lineGroupsProcessor = new LineGroupsProcessor(testGroupList);
        lineGroupsProcessor.sortAndCount();

        int expectedGrCount = 1;
        assertEquals(expectedGrCount, lineGroupsProcessor.getGroupCount());

        SortedSet<List<String>> sortedLineGroups = lineGroupsProcessor.getSortedLineGroups();
        assertNotNull(sortedLineGroups);
        int expectedTotalGrCount = 1;
        assertEquals(expectedTotalGrCount, sortedLineGroups.size());

        List<String> firstGroupFromSortedSet = sortedLineGroups.first();
        List<String> firstGroupFromSourceGroupList = testGroupList.get(0);
        assertEquals(firstGroupFromSortedSet,firstGroupFromSourceGroupList);
    }
}