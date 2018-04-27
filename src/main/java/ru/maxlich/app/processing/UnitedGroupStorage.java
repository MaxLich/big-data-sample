package ru.maxlich.app.processing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mshulakov on 27.04.2018.
 */
// содержит информации о всех сляниях групп строк
public class UnitedGroupStorage {
    Map<Integer, Integer> unitedGroups = new HashMap<>(); //мэп с парами "номер первой группы - номер второй группы, в которую были перенесены все элементы из первой группы"

    private static UnitedGroupStorage ourInstance = new UnitedGroupStorage();

    public static UnitedGroupStorage getInstance() {
        return ourInstance;
    }

    private UnitedGroupStorage() {
    }

    public Integer getFinalGroupNumber(Integer startingGrNum) {
        while (unitedGroups.containsKey(startingGrNum)) // если группа с таким номером объединена с другой,
            startingGrNum = unitedGroups.get(startingGrNum); //то получаем номер группы, с которой была объединена данная
        return startingGrNum;
    }

    public Integer put(Integer firstGrNum, Integer secondGrNum) {
        return unitedGroups.put(firstGrNum, secondGrNum);
    }
}
