package ru.maxlich.app.processing.finder;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ColumnStorageTest {
    private static ColumnStorage columnStorage;

    @Before
    public void setUp() throws Exception {
        columnStorage = ColumnStorage.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        columnStorage.clear();
    }

    @Test
    public void testAddColumnIfNotExists() {
        int startingColCount = columnStorage.getColumnCount();
        int columnNumber = 1;
        boolean isAdded = columnStorage.addColumnIfNotExists(columnNumber);
        int colCountAfterAdding = columnStorage.getColumnCount();
        assertEquals((colCountAfterAdding - startingColCount), columnNumber + 1);
        assertTrue(isAdded);
    }

    @Test
    public void testAddColumnIfNotExistsWithNegativeColNum() {
        int startingColCount = columnStorage.getColumnCount();
        int columnNumber = -10;
        boolean isAdded = columnStorage.addColumnIfNotExists(columnNumber);
        int colCountAfterAdding = columnStorage.getColumnCount();
        assertEquals(colCountAfterAdding, startingColCount);
        assertFalse(isAdded);
    }

    @Test
    public void testAddColumnIfNotExistsWithExistingColNum() {
        columnStorage.addColumnIfNotExists(0);
        int startingColCount = columnStorage.getColumnCount();
        int columnNumber = 0;
        boolean isAdded = columnStorage.addColumnIfNotExists(columnNumber);
        int colCountAfterAdding = columnStorage.getColumnCount();
        assertEquals(colCountAfterAdding, startingColCount);
        assertFalse(isAdded);
    }

    @Test
    public void testGetColumnCountWithEmptyList() throws Exception {
        int colCount = columnStorage.getColumnCount();
        int expectedColCount = 0;
        assertEquals(colCount,expectedColCount);
    }

    @Test
    public void testClear() throws Exception {
        columnStorage.addColumnIfNotExists(10);
        int startingColCount = columnStorage.getColumnCount();
        columnStorage.clear();
        int colCountAfterCleaning = columnStorage.getColumnCount();
        assertNotEquals(startingColCount,colCountAfterCleaning);
        int expectedColCount = 0;
        assertEquals(colCountAfterCleaning,expectedColCount);
    }

    @Test
    public void testPutAndGetNewElement() throws Exception {
        int columnNum = 0;
        String lineElement = "a";
        LineElement newElement = new LineElement(lineElement, columnNum);
        int groupNumber = 0;
        columnStorage.putNewElement(newElement, groupNumber);

        Integer groupNumberFromColumn = columnStorage.getGroupNumberBy(columnNum, lineElement);
        assertNotNull(groupNumberFromColumn);
        assertEquals(new Integer(groupNumber), groupNumberFromColumn);
    }

    @Test
    public void testPutNewElementToNonExistentColumn() throws Exception {
        int columnNum = 10;
        String lineElement = "a1";
        LineElement newElement = new LineElement(lineElement, columnNum);
        int groupNumber = 0;
        columnStorage.putNewElement(newElement, groupNumber);

        Integer groupNumberFromColumn = columnStorage.getGroupNumberBy(columnNum, lineElement);
        assertNotNull(groupNumberFromColumn);
        assertEquals(new Integer(groupNumber), groupNumberFromColumn);
    }

    @Test
    public void testGetGroupNumberByWithZeroColNumAndNonExistentElem() {
        int columnNumber = 0;
        String lineElement = "x";
        Integer groupNumber = columnStorage.getGroupNumberBy(columnNumber, lineElement);
        assertNull(groupNumber);
    }

    @Test
    public void testGetGroupNumberByWithNegativeColNum() {
        int columnNumber = -10;
        String lineElement = "";
        Integer groupNumber = columnStorage.getGroupNumberBy(columnNumber, lineElement);
        assertNull(groupNumber);
    }

    @Test
    public void testGetGroupNumberBy() {
        int columnNumber = 0;
        String lineElement = "";
        Integer groupNumber = columnStorage.getGroupNumberBy(columnNumber, lineElement);
        assertNull(groupNumber);
    }


    @Test
    public void testPutNewElementsToColumns() {
        int groupNumber = 11;
        int colCount = 3;
        int[] colNums = new int[colCount];
        String[] lineElements = new String[colCount];
        for (int i = 0; i < colNums.length; i++) {
            colNums[0] = i;
        }
        lineElements[0] = "a";
        lineElements[1] = "b";
        lineElements[2] = "c";

        List<LineElement> newElements = new ArrayList<>();
        for (int i = 0; i < colCount; i++) {
            newElements.add(new LineElement(lineElements[i], colNums[i]));
        }
        columnStorage.putNewElementsToColumns(newElements, groupNumber);

        for (int i = 0; i < colNums.length; i++) {
            Integer groupNumberFromColumn = columnStorage.getGroupNumberBy(colNums[i], lineElements[i]);
            assertNotNull(groupNumberFromColumn);
            assertEquals(new Integer(groupNumber), groupNumberFromColumn);
        }
    }
}