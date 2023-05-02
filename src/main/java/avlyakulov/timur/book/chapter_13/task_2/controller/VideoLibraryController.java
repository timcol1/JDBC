package avlyakulov.timur.book.chapter_13.task_2.controller;

import avlyakulov.timur.book.chapter_13.task_2.insert.InsertDataToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VideoLibraryController {
    public void run() {
        String position;
        runNavigation();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((position = reader.readLine()) != null) {
                if (position.equals("0"))
                    System.exit(0);
                else crud(position);
                runNavigation();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("If you want to insert data to tables press 1");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String position) {
        switch (position) {
            case "1" -> insertDataToTable();
        }
    }

    private void insertDataToTable () {
        new InsertDataToDB().insertToTableData();
    }
}