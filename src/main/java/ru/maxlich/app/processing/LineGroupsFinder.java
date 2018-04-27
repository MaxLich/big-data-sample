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
        //List<Map<String, Integer>> columns = new ArrayList<>(); // список стобцов, каждый столбец - мапа с парами "элемент строки/столбца - номер группы"
        Map<Integer, Integer> unitedGroups = new HashMap<>(); //мэп с парами "номер некоторой группы - номер группы, с которой надо объединить данную группу"

        TreeSet<Integer> groupsWithSameElems = new TreeSet<>(); //список групп, имеющих совпадающие элементы
        List<LineElement> newElements = new ArrayList<>(); //список элементов, которых нет в мапах столбцов

        for (String line : lines) {
            groupsWithSameElems.clear();
            newElements.clear();

            String[] lineElements = line.split(";");
            walkLineElements(lineElements,/* columns, */unitedGroups, groupsWithSameElems, newElements);

            int groupNumber = getGroupNumber(groupsWithSameElems);
            //putNewElementsToColumns(columns, newElements, groupNumber);
            columnStorage.putNewElementsToColumns(newElements,groupNumber);
            checkGroups(unitedGroups, groupsWithSameElems, groupNumber);

            linesGroups.get(groupNumber).add(line);
        }

        linesGroups.removeAll(Collections.<List<String>>singletonList(null)); //удаляем несуществующие группы

    }

    private void checkGroups(Map<Integer, Integer> unitedGroups, TreeSet<Integer> groupsWithSameElems, int groupNumber) {
        for (int matchedGrNum : groupsWithSameElems) { //перебираем все группы с таким же элементом
            if (matchedGrNum != groupNumber) {
                unitedGroups.put(matchedGrNum, groupNumber); //сохраняем инф-цию об объединённых группах
                linesGroups.get(groupNumber).addAll(linesGroups.get(matchedGrNum)); //объединяем группы
                linesGroups.set(matchedGrNum, null); //помечаем группу с текущим номер как несуществующую
            }
        }
    }

   /* private void putNewElementsToColumns(List<Map<String, Integer>> columns, List<LineElement> newElements, int groupNumber) {
        for (NewLineElement newLineElement : newElements) {
            columns.get(newLineElement.columnNum).put(newLineElement.lineElement, groupNumber);
        }
    }*/

    private int getGroupNumber(TreeSet<Integer> groupsWithSameElems) {
        if (groupsWithSameElems.isEmpty()) {
            linesGroups.add(new ArrayList<>());
            return linesGroups.size() - 1;
        } else {
            return groupsWithSameElems.first();
        }
    }

    private void walkLineElements(String[] lineElements, /*List<Map<String, Integer>> columns, */Map<Integer, Integer> unitedGroups,
                                  TreeSet<Integer> groupsWithSameElems, List<LineElement> newElements) {
        for (int elmIndex = 0; elmIndex < lineElements.length; elmIndex++) {
            String currLnElem = lineElements[elmIndex];
            /*if (columns.size() == elmIndex)
                columns.add(new HashMap<>());*/
            columnStorage.addColumnIfNotExists(elmIndex);
            if ("".equals(currLnElem.replaceAll("\"","").trim()))
                continue;

/*            Map<String, Integer> currCol = columns.get(elmIndex);
            Integer elemGrNum = currCol.get(currLnElem);*/
            Integer elemGrNum = columnStorage.getGroupNumberBy(elmIndex, currLnElem);
            if (elemGrNum != null) {
                while (unitedGroups.containsKey(elemGrNum)) // если группа с таким номером объединена с другой,
                    elemGrNum = unitedGroups.get(elemGrNum); //то сохраняем номер группы, с которой была объединена данная
                groupsWithSameElems.add(elemGrNum);
            } else {
                newElements.add(new LineElement(currLnElem, elmIndex));
            }
        }
    }


    //Getters and Setters
    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    //служебный класс для элементов строки, которые мы ещё не встречали в столбце
  /*  static class NewLineElement {
        private String lineElement;
        private int columnNum;

        private NewLineElement(String lineElement, int columnNum) {
            this.lineElement = lineElement;
            this.columnNum = columnNum;
        }
    }*/
}
