package ru.maxlich.app.processing;

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

    public void addColumnIfNotExists(int columnNumber) {
        int lastIndex = columns.size() - 1;
        int diff = lastIndex - columnNumber;
        if (diff < 0) {
            for (int i = 0; i < Math.abs(diff); i++) {
                columns.add(new HashMap<>());
            }
        }
    }

    public Integer getGroupNumberBy(int colNum, String elem) {
        return columns.get(colNum).get(elem);
    }

    public void putNewElementsToColumns(List<LineElement> newElements, int groupNumber) {
        for (LineElement newLineElement : newElements) {
            columns.get(newLineElement.getColumnNum()).put(newLineElement.getLineElement(), groupNumber);
        }
    }

    /*public static void main(String[] args) {
        ColumnStorage columnStorage = ColumnStorage.getInstance();
        columnStorage.addColumnIfNotExists(0);
        columnStorage.addColumnIfNotExists(1);
        columnStorage.putNewElementsToColumns(
                Arrays.asList(
                        new LineElement("x", 0)
                        , new LineElement("y", 0)
                        , new LineElement("a", 1))
                , 0);

        Integer o = columnStorage.getGroupNumberBy(0, "o");
        System.out.println(o);
    }*/

}
