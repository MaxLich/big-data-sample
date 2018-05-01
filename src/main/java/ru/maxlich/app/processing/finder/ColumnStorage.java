package ru.maxlich.app.processing.finder;

import java.util.*;

//содержит список столбцов, даёт к ним доступ
public class ColumnStorage {
    private static ColumnStorage ourInstance = new ColumnStorage();

    public static ColumnStorage getInstance() {
        return ourInstance;
    }

    private ColumnStorage() {
    }

    private List<Map<String, Integer>> columns = new ArrayList<>(); // список стобцов, каждый столбец - мапа с парами "элемент строки/столбца - номер группы"

    public boolean addColumnIfNotExists(int columnNumber) {
        if (columnNumber < 0)
            return false;

        int lastIndex = columns.size() - 1;
        int diff = lastIndex - columnNumber;
        if (diff < 0) {
            for (int i = 0; i < Math.abs(diff); i++) {
                columns.add(new HashMap<>());
            }
            return true;
        }

        return false;
    }

    public Integer getGroupNumberBy(int colNum, String elem) {
        if (colNum < 0)
            return null;

        addColumnIfNotExists(colNum);
        Map<String, Integer> column = columns.get(colNum);
        return (column == null) ? null : column.get(elem);
    }

    public void putNewElementsToColumns(List<LineElement> newElements, int groupNumber) {
        for (LineElement newLineElement : newElements) {
            int colNum = newLineElement.getColumnNum();
            addColumnIfNotExists(colNum);
            columns.get(colNum).put(newLineElement.getLineElement(), groupNumber);
        }
    }

    public void putNewElement(LineElement newLineElement, int groupNumber) {
        int colNum = newLineElement.getColumnNum();
        addColumnIfNotExists(colNum);
        columns.get(colNum).put(newLineElement.getLineElement(), groupNumber);
    }


    public int getColumnCount() {
        return columns.size();
    }

    public void clear() {
        columns.clear();
    }

}
