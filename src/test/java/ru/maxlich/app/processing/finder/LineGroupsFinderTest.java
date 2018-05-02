package ru.maxlich.app.processing.finder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class LineGroupsFinderTest {

    @Test
    public void testFind() {
        List<String> testLines = Arrays.asList("a;b;c", "a;b;d", "x;y;z", "o;o;o",";;","p;;");
        List<List<String>> groupList = new LineGroupsFinder(testLines).find();

        List<List<String>> testGroupList = new ArrayList<>();

        testGroupList.add(testLines.subList(0,2));
        testGroupList.add(testLines.subList(2,3));
        testGroupList.add(testLines.subList(3,4));
        testGroupList.add(testLines.subList(4,5));
        testGroupList.add(testLines.subList(5,6));

        assertEquals(testGroupList.size(), groupList.size());
        boolean isNotMatched = false;
        GroupCycle:
        for (List<String> testGroup : testGroupList) {
            int groupNum, prevGrNum = -1;
            for (String testLine: testGroup) {
                groupNum = findInGroupList(groupList, testLine);
                if (groupNum == -1 || (prevGrNum != -1 && groupNum != prevGrNum)) {
                    isNotMatched = true;
                    break GroupCycle;
                }
                prevGrNum = groupNum;
            }
        }
        assertFalse(isNotMatched);
    }

    private int findInGroupList(List<List<String>> groupList, String line) {
        for (int i = 0; i < groupList.size(); i++) {
            List<String> group = groupList.get(i);
            if (group.contains(line)) {
                return i;
            }
        }

        return -1;
    }

    @Test
    public void testFindWithNullLineList() {
        List<String> testLines = null;
        List<List<String>> groupList = new LineGroupsFinder(testLines).find();
        assertTrue(groupList.isEmpty());
    }

    @Test
    public void testFindWithEmptyLineList() {
        List<String> testLines = Collections.emptyList();
        List<List<String>> groupList = new LineGroupsFinder(testLines).find();
        assertTrue(groupList.isEmpty());
    }

    @Test
    public void testFindWithSingleLineList() {
        List<String> testLines = Collections.singletonList("a;b;c");
        List<List<String>> groupList = new LineGroupsFinder(testLines).find();
        int expectedGroupCount = 1;
        assertEquals(expectedGroupCount, groupList.size());
        List<String> firstGroup = groupList.get(0);
        assertEquals(testLines, firstGroup);
    }
}