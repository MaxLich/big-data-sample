package ru.maxlich.app;

import ru.maxlich.app.file.FileLineReader;
import ru.maxlich.app.file.FileLineWriter;
import ru.maxlich.app.file.FileUniqueLineReader;
import ru.maxlich.app.processing.LineGroupsFinder;
import ru.maxlich.app.processing.LineGroupsProcessor;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by mshulakov on 24.04.2018.
 */
public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Ошибка! Пути к файлам для входных и выходных данных не заданы!");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        FileLineReader fileReader = new FileUniqueLineReader(inputFilePath);
        FileLineWriter fileWriter = new FileLineWriter(outputFilePath);

        long start = System.currentTimeMillis();

        System.out.println("Путь к файлу с входными данными: " + inputFilePath);

        System.out.println("Считывание данных с файла...");
        List<String> lines;
        try {
            lines = fileReader.readLines();
            System.out.println("Файл успешно прочитан" + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Произошла ошибка! Подробности: " + e);
            return;
        }

        System.out.println("Поиск групп строк...");
        List<List<String>> lineGroups = new LineGroupsFinder(lines).find();
        System.out.println("Поиск закончен." + System.lineSeparator());

        System.out.println("Сортировка групп строк...");
        LineGroupsProcessor groupsProcessor = new LineGroupsProcessor(lineGroups);
        groupsProcessor.sortAndCount();
        Set<List<String>> sortedLineGroups = groupsProcessor.getSortedLineGroups();
        int groupCount = groupsProcessor.getGroupCount();
        System.out.println("Сортировка закончена." + System.lineSeparator());

        System.out.println("Путь к файлу с выходными данными: " + outputFilePath);
        System.out.println("Запись данных в файл...");
        try {
            fileWriter.writeLinesGroups(groupCount,sortedLineGroups);
            System.out.println("Данные успешно записаны в файл." + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Произошла ошибка! Подробности: " + e);
            return;
        }

        long stop = System.currentTimeMillis();
        System.out.println("Общее время работы: " + (stop - start) + " мс");
    }
}
