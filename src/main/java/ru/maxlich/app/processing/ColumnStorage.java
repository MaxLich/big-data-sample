package ru.maxlich.app.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//содержит список столбцов, даёт к ним доступ
public class ColumnStorage {
    private static ColumnStorage ourInstance = new ColumnStorage();

    public static ColumnStorage getInstance() {
        return ourInstance;
    }

    private ColumnStorage() {
    }

    List<Map<String, Integer>> columns = new ArrayList<>(); // список стобцов, каждый столбец - мапа с парами "элемент строки/столбца - номер группы"



}
