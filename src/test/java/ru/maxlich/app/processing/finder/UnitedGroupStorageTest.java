package ru.maxlich.app.processing.finder;

import org.junit.*;

import static org.junit.Assert.*;

public class UnitedGroupStorageTest {
    private static UnitedGroupStorage storage;

    @BeforeClass
    public static void setUpAll() throws Exception {
        storage = UnitedGroupStorage.getInstance();
    }

    @AfterClass
    public static void tearDownAll() throws Exception {
        storage = null;
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    @Test
    public void testPutAndGet() {
        Integer firstGrNum = 0;
        Integer secondGrNum = 1;
        storage.put(firstGrNum, secondGrNum);

        Integer destGroupNum = storage.getDestGroupBy(firstGrNum);
        assertEquals(secondGrNum, destGroupNum);
    }

    @Test
    public void testPutPairWithNullKey() {
        UnitedGroupStorage storage = UnitedGroupStorage.getInstance();
        Integer firstGrNum = null;
        Integer secondGrNum = 1;
        storage.put(firstGrNum, secondGrNum);

        Integer destGroupNum = storage.getDestGroupBy(firstGrNum);
        assertNotEquals(secondGrNum, destGroupNum);
        assertNull(destGroupNum);
    }

    @Test
    public void testPutPairWithNegativeKey() {
        UnitedGroupStorage storage = UnitedGroupStorage.getInstance();
        Integer firstGrNum = -12;
        Integer secondGrNum = 25;
        storage.put(firstGrNum, secondGrNum);

        Integer destGroupNum = storage.getDestGroupBy(firstGrNum);
        assertNotEquals(secondGrNum, destGroupNum);
        assertNull(destGroupNum);
    }


    @Test
    public void testGetFinalGroupNumberWithNullStartingGrNum() {
        Integer startingGrNum = null;
        Integer finalGroupNumber = storage.getFinalGroupNumber(startingGrNum);
        assertNull(finalGroupNumber);
    }

    @Test
    public void testGetFinalGroupNumberWithNegativeStartingGrNum() {
        Integer startingGrNum = -20;
        Integer finalGroupNumber = storage.getFinalGroupNumber(startingGrNum);
        assertNull(finalGroupNumber);
    }

    @Test
    public void testGetFinalGroupNumber() {
        final Integer startingGrNum = 1;
        Integer currGrNum = startingGrNum;
        for (int i = 0; i < 5; i++) {
            storage.put(currGrNum,++currGrNum);
        }
        final Integer expectedFinalGrNum = currGrNum;

        Integer finalGrNumFromStorage = storage.getFinalGroupNumber(startingGrNum);
        assertNotNull(finalGrNumFromStorage);
        assertEquals(expectedFinalGrNum, finalGrNumFromStorage);
    }

    @Test
    public void testGetPairCount() {
        storage.put(1,2);

        int pairCount = storage.getPairCount();
        int expectedPairCount = 1;
        assertEquals(expectedPairCount, pairCount);
    }

    @Test
    public void testGetPairCountWithEmptyStorage() {
        int pairCount = storage.getPairCount();
        int expectedPairCount = 0;
        assertEquals(expectedPairCount, pairCount);
    }

    @Test
    public void testClearWithEmptyStorage() {
        int pairCountBeforeCleaning = storage.getPairCount();
        storage.clear();
        int pairCountAfterCleaning = storage.getPairCount();
        int expectedPairCount = 0;
        assertEquals(expectedPairCount, pairCountAfterCleaning);
        assertEquals(pairCountBeforeCleaning, pairCountAfterCleaning);
    }

    @Test
    public void testClear() {
        storage.put(1,2);
        int pairCountBeforeCleaning = storage.getPairCount();

        storage.clear();
        int pairCountAfterCleaning = storage.getPairCount();

        int expectedPairCount = 0;
        assertEquals(expectedPairCount, pairCountAfterCleaning);
        assertNotEquals(pairCountBeforeCleaning, pairCountAfterCleaning);
    }
}