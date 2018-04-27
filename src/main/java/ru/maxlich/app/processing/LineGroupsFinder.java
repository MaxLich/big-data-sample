package ru.maxlich.app.processing;

import java.util.*;

/**
 * Created by mshulakov on 25.04.2018.
 */
// находит группы строк
public class LineGroupsFinder {
    private List<String> lines; //строки, которые будем обрабатывать
    private final List<List<String>> linesGroups; //списки групп строк, которые получаем в результате обработки

    private ColumnStorage columnStorage = ColumnStorage.getInstance();
    private UnitedGroupStorage unitedGroupStorage = UnitedGroupStorage.getInstance();

    public LineGroupsFinder(List<String> lines) {
        this.lines = lines;
        linesGroups = new ArrayList<>();
    }

    //группировка строк, главный метод
    public List<List<String>> find() {
        if (lines == null)
            return Collections.emptyList();

        if (lines.size() < 2) {
            linesGroups.add(lines);
            return linesGroups;
        }

        processAllLines(lines);
        return linesGroups;
    }

    //вспомогательный метод для обработки строк
    private void processAllLines(List<String> lines) {
       // Map<Integer, Integer> unitedGroups = new HashMap<>(); //мэп с парами "номер некоторой группы - номер группы, с которой надо объединить данную группу"

        TreeSet<Integer> groupsWithSameElems = new TreeSet<>(); //список групп, имеющих совпадающие элементы
        List<LineElement> newElements = new ArrayList<>(); //список элементов, которых нет в мапах столбцов

        for (String line : lines) {
            groupsWithSameElems.clear();
            newElements.clear();

            String[] lineElements = line.split(";");
            walkLineElements(lineElements,/* unitedGroups,*/ groupsWithSameElems, newElements);

            int groupNumber = getGroupNumber(groupsWithSameElems);
            columnStorage.putNewElementsToColumns(newElements,groupNumber);
            checkGroups(/*unitedGroups,*/ groupsWithSameElems, groupNumber);

            linesGroups.get(groupNumber).add(line);
        }

        linesGroups.removeAll(Collections.<List<String>>singletonList(null)); //удаляем несуществующие группы
    }

    private void checkGroups(/*Map<Integer, Integer> unitedGroups,*/ TreeSet<Integer> groupsWithSameElems, int groupNumber) {
        for (int matchedGrNum : groupsWithSameElems) { //перебираем все группы с таким же элементом
            if (matchedGrNum != groupNumber) {
               // unitedGroups.put(matchedGrNum, groupNumber); //сохраняем инф-цию об объединённых группах
                unitedGroupStorage.put(matchedGrNum, groupNumber);
                linesGroups.get(groupNumber).addAll(linesGroups.get(matchedGrNum)); //объединяем группы
                linesGroups.set(matchedGrNum, null); //помечаем группу с текущим номер как несуществующую
            }
        }
    }

    private int getGroupNumber(TreeSet<Integer> groupsWithSameElems) {
        if (groupsWithSameElems.isEmpty()) {
            linesGroups.add(new ArrayList<>());
            return linesGroups.size() - 1;
        } else {
            return groupsWithSameElems.first();
        }
    }

    private void walkLineElements(String[] lineElements, /*Map<Integer, Integer> unitedGroups,*/
                                  TreeSet<Integer> groupsWithSameElems, List<LineElement> newElements) {
        for (int elmIndex = 0; elmIndex < lineElements.length; elmIndex++) {
            String currLnElem = lineElements[elmIndex];
            columnStorage.addColumnIfNotExists(elmIndex);
            if ("".equals(currLnElem.replaceAll("\"","").trim()))
                continue;

            Integer elemGrNum = columnStorage.getGroupNumberBy(elmIndex, currLnElem);
            if (elemGrNum != null) {
                //elemGrNum = getFinalGroupNumber(unitedGroups, elemGrNum);
                elemGrNum = unitedGroupStorage.getFinalGroupNumber(elemGrNum);
                groupsWithSameElems.add(elemGrNum);
            } else {
                newElements.add(new LineElement(currLnElem, elmIndex));
            }
        }
    }

   /* private Integer getFinalGroupNumber(Map<Integer, Integer> unitedGroups, Integer elemGrNum) {
        while (unitedGroups.containsKey(elemGrNum)) // если группа с таким номером объединена с другой,
            elemGrNum = unitedGroups.get(elemGrNum); //то получаем номер группы, с которой была объединена данная
        return elemGrNum;
    }*/


    //Getters and Setters
    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
