package ru.maxlich.app.processing.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by mshulakov on 27.04.2018.
 */
public class LineElementsChecker {
    private ColumnStorage columnStorage = ColumnStorage.getInstance();
    private UnitedGroupStorage unitedGroupStorage = UnitedGroupStorage.getInstance();

    private TreeSet<Integer> groupsWithSameElems = new TreeSet<>(); //список групп, имеющих совпадающие элементы
    private List<LineElement> newElements = new ArrayList<>(); //список элементов, которых нет в мапах столбцов

    public void checkAll(String[] lineElements) {
        clearResultCollections();
        for (int elmIndex = 0; elmIndex < lineElements.length; elmIndex++) {
            String currLnElem = lineElements[elmIndex];
            columnStorage.addColumnIfNotExists(elmIndex);
            if ("".equals(currLnElem.replaceAll("\"","").trim()))
                continue;

            Integer elemGrNum = columnStorage.getGroupNumberBy(elmIndex, currLnElem);
            if (elemGrNum != null) {
                elemGrNum = unitedGroupStorage.getFinalGroupNumber(elemGrNum);
                groupsWithSameElems.add(elemGrNum);
            } else {
                newElements.add(new LineElement(currLnElem, elmIndex));
            }
        }
    }

    private void clearResultCollections() {
        groupsWithSameElems.clear();
        newElements.clear();
    }

    //Getters
    public TreeSet<Integer> getGroupsWithSameElems() {
        return new TreeSet<>(groupsWithSameElems);
    }

    public List<LineElement> getNewElements() {
        return new ArrayList<>(newElements);
    }
}
