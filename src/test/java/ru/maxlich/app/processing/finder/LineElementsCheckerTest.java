package ru.maxlich.app.processing.finder;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class LineElementsCheckerTest {
    private static ColumnStorage columnStorage;
    private LineElementsChecker checker;

    @BeforeClass
    public static void setUpAll() throws Exception {
        columnStorage = ColumnStorage.getInstance();
    }

    @AfterClass
    public static void tearDownAll() throws Exception {
        columnStorage = null;
    }

    @Before
    public void setUp() throws Exception {
        checker = new LineElementsChecker();
    }

    @After
    public void tearDown() throws Exception {
        columnStorage.clear();
    }

    @Test
    public void testCheckAllWithAllNewElements() {
        String[] testLineElements = {"a", "b", "c"};
        checker.checkAll(testLineElements);

        TreeSet<Integer> groupsWithSameElems = checker.getGroupsWithSameElems();
        List<LineElement> newElements = checker.getNewElements();

        assertTrue(groupsWithSameElems.isEmpty());
        assertFalse(newElements.isEmpty());
        assertEquals(testLineElements.length, newElements.size());

        boolean isNotContained = false;
        for (LineElement newLineElement : newElements) {
            if (!newLineElement.getLineElement().equals(testLineElements[newLineElement.getColumnNum()])) {
                isNotContained = true;
                break;
            }
        }
        assertFalse(isNotContained);
    }

    @Test
    public void testCheckAllWithAllRepeatingElements() {
        ColumnStorage columnStorage = ColumnStorage.getInstance();

        String[][] sourceLines = {
                {"a", "b", "c"},
                {"x", "y", "z"},
                {"c", "d", "x"},
        };

        int[] grNums = new int[sourceLines.length];
        int currGrNum;
        for (int i = 0; i < sourceLines.length; i++) {
            String[] line = sourceLines[i];
            List<LineElement> tempLnElemList = getLineElementList(line);
            currGrNum = i+1;
            grNums[i] = currGrNum;
            columnStorage.putNewElementsToColumns(tempLnElemList, currGrNum);
        }

        String[] testLineElements = {"a", "y", "x"};

        checker.checkAll(testLineElements);
        TreeSet<Integer> groupsWithSameElems = checker.getGroupsWithSameElems();
        List<LineElement> newElements = checker.getNewElements();

        assertFalse(groupsWithSameElems.isEmpty());
        assertTrue(newElements.isEmpty());

        assertEquals(sourceLines.length, groupsWithSameElems.size());
        boolean isNotContained = false;
        for (int grNum : grNums) {
            if (!groupsWithSameElems.contains(grNum)) {
                isNotContained = true;
                break;
            }
        }
        assertFalse(isNotContained);
    }

    private List<LineElement> getLineElementList(String[] testLineElements) {
        List<LineElement> tempLnElemList = new ArrayList<>();
        for (int i = 0; i < testLineElements.length; i++) {
            tempLnElemList.add(new LineElement(testLineElements[i], i));
        }
        return tempLnElemList;
    }

    @Test
    public void testCheckAllWithDifferentElements() {
        ColumnStorage columnStorage = ColumnStorage.getInstance();

        String[][] sourceLines = {
                {"a", "b", "c"},
                {"x", "y", "z"},
                {"c", "d", "x"},
        };

        int[] grNums = new int[sourceLines.length];
        int currGrNum;
        for (int i = 0; i < sourceLines.length; i++) {
            String[] line = sourceLines[i];
            List<LineElement> tempLnElemList = getLineElementList(line);
            currGrNum = i+1;
            grNums[i] = currGrNum;
            columnStorage.putNewElementsToColumns(tempLnElemList, currGrNum);
        }

        String[] testLineElements = {"a", "y", "b"};

        checker.checkAll(testLineElements);
        TreeSet<Integer> groupsWithSameElems = checker.getGroupsWithSameElems();
        List<LineElement> newElements = checker.getNewElements();

        assertFalse(groupsWithSameElems.isEmpty());
        assertFalse(newElements.isEmpty());

        // проверка множества с номерами групп, в которых уже есть такие элементы
        int expectedGrCountWithSameElems = 2;
        assertEquals(expectedGrCountWithSameElems, groupsWithSameElems.size());

        boolean[] isGroupContained = new boolean[grNums.length];
        for (int i = 0; i < isGroupContained.length; i++) {
            isGroupContained[i] = groupsWithSameElems.contains(grNums[i]);
        }

        assertTrue(isGroupContained[0] && isGroupContained[1]);
        assertFalse(isGroupContained[2]);

        // проверка списка новых элементов
        int expectedNewElemListSize = 1;
        assertEquals(expectedNewElemListSize, newElements.size());

        LineElement newElem = newElements.get(0);
        int expectedColumnNum = 2;
        boolean isSameElement = testLineElements[2].equals(newElem.getLineElement())
                && newElem.getColumnNum() == expectedColumnNum;
        assertTrue(isSameElement);
    }
}