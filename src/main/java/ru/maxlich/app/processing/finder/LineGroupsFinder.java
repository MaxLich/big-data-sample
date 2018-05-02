package ru.maxlich.app.processing.finder;

import java.util.*;

/**
 * Created by mshulakov on 25.04.2018.
 */
// находит группы строк
public class LineGroupsFinder {
    private List<String> lines; //строки, которые будем обрабатывать
    private final List<List<String>> lineGroups; //списки групп строк, которые получаем в результате обработки

    private ColumnStorage columnStorage = ColumnStorage.getInstance();
    private UnitedGroupStorage unitedGroupStorage = UnitedGroupStorage.getInstance();

    public LineGroupsFinder(List<String> lines) {
        this.lines = lines;
        lineGroups = new ArrayList<>();
    }

    //группировка строк, главный метод
    public List<List<String>> find() {
        if (lines == null || lines.isEmpty())
            return Collections.emptyList();

       if (lines.size() == 1) {
            lineGroups.add(lines);
            return lineGroups;
        }

        LineElementsChecker checker = new LineElementsChecker();

        for (String line : lines) {
            String[] lineElements = line.split(";");

            checker.checkAll(lineElements); //проверяем, встречались ли уже такие элементы или нет
            TreeSet<Integer> groupsWithSameElems = checker.getGroupsWithSameElems();
            List<LineElement> newElements = checker.getNewElements();

            int groupNumber = getGroupNumber(groupsWithSameElems);
            columnStorage.putNewElementsToColumns(newElements, groupNumber);
            checkGroups(groupsWithSameElems, groupNumber); //проверяем, нужно ли объединять какие-нибудь группы

            lineGroups.get(groupNumber).add(line);
        }

        lineGroups.removeAll(Collections.<List<String>>singletonList(null)); //удаляем несуществующие группы

        return lineGroups;
    }

    private void checkGroups(TreeSet<Integer> groupsWithSameElems, int groupNumber) {
        for (int matchedGrNum : groupsWithSameElems) { //перебираем все группы с такими же элементами
            if (matchedGrNum != groupNumber) {
                unitedGroupStorage.put(matchedGrNum, groupNumber); //сохраняем инф-цию об объединяемых группах
                lineGroups.get(groupNumber).addAll(lineGroups.get(matchedGrNum)); //объединяем группы
                lineGroups.set(matchedGrNum, null); //помечаем группу с текущим номером как несуществующую
            }
        }
    }

    private int getGroupNumber(TreeSet<Integer> groupsWithSameElems) {
        if (groupsWithSameElems.isEmpty()) {
            lineGroups.add(new ArrayList<>());
            return lineGroups.size() - 1;
        } else {
            return groupsWithSameElems.first();
        }
    }

    //Getters and Setters
    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
