package ru.maxlich.app.processing;

import java.util.*;

/**
 * Created by mshulakov on 25.04.2018.
 */
// обрабатывает готовый список групп строк:
// а) сортирует группы в порядке убывания их размера (количества строк группы),
// б) считает количество групп размером больше 1
public class LineGroupsProcessor {
    private List<List<String>> lineGroups; //входящий список групп строк

    private int groupCount; //количество групп, в которых больше одной строки
    private SortedSet<List<String>> lineGroupsSet; //отсортированный список групп

    public LineGroupsProcessor(List<List<String>> lineGroups) {
        this.lineGroups = lineGroups;
        this.lineGroupsSet = new TreeSet<>(new LineGroupComparator());
    }

    public LineGroupsProcessor sortAndCount() {
        if (lineGroups == null)
            return null;

        groupCount = 0;
        lineGroupsSet.clear();
        for (List<String> lineGroup : lineGroups) {
            if (lineGroup.size() > 1) {
                groupCount++;
            }
            lineGroupsSet.add(lineGroup);
        }

        return this;
    }


    //Getters and Setters
    public List<List<String>> getLineGroups() {
        return lineGroups == null? null : Collections.unmodifiableList(lineGroups);
    }

    public void setLineGroups(List<List<String>> lineGroups) {
        this.lineGroups = lineGroups;
    }

    public SortedSet<List<String>> getSortedLineGroups() {
        return Collections.unmodifiableSortedSet(lineGroupsSet);
    }

    public int getGroupCount() {
        return groupCount;
    }


    //служебный класс для сравнения групп между собой
    private static class LineGroupComparator implements Comparator<List<String>> {
        @Override
        public int compare(List<String> group1, List<String> group2) {
            int diff = group2.size() - group1.size();
            if (diff != 0)
                return diff;

            Iterator<String> iterator1 = group1.iterator();
            Iterator<String> iterator2 = group2.iterator();
            while (iterator1.hasNext()) {
                diff = iterator1.next().compareTo(iterator2.next());
                if (diff != 0)
                    return diff;
            }

            return 0;
        }
    }
}
