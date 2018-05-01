package ru.maxlich.app.processing.finder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mshulakov on 27.04.2018.
 */
// содержит информации о всех слияниях групп строк
public class UnitedGroupStorage {
    private Map<Integer, Integer> unitedGroups = new HashMap<>(); //мэп с парами "номер исходной группы - номер группы, в которую были перенесены все элементы из исходной группы"

    private static UnitedGroupStorage ourInstance = new UnitedGroupStorage();

    public static UnitedGroupStorage getInstance() {
        return ourInstance;
    }

    private UnitedGroupStorage() {
    }

    public Integer getDestGroupBy(Integer sourceGrNum) {
        if (sourceGrNum == null || sourceGrNum < 0)
            return null;

        return unitedGroups.get(sourceGrNum);
    }

    public Integer getFinalGroupNumber(Integer startingGrNum) {
        if (startingGrNum == null || startingGrNum < 0)
            return null;

        while (unitedGroups.containsKey(startingGrNum)) // если группа с таким номером объединена с другой,
            startingGrNum = unitedGroups.get(startingGrNum); //то получаем номер группы, с которой была объединена данная
        return startingGrNum;
    }

    public Integer put(Integer firstGrNum, Integer secondGrNum) {
        if (firstGrNum == null || firstGrNum < 0)
            return null;
        if (secondGrNum == null || secondGrNum < 0)
            return null;

        return unitedGroups.put(firstGrNum, secondGrNum);
    }

    public int getPairCount() {
        return unitedGroups.size();
    }

    public void clear() {
        unitedGroups.clear();
    }
}
