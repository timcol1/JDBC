package avlyakulov.timur.book.chapter_13.task_3.controller;

import avlyakulov.timur.book.chapter_13.task_3.crud.OperateDataDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SchoolController {
    OperateDataDB operateDataDB = new OperateDataDB();
    public void run() {
        String answer;
        runNavigation();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((answer = reader.readLine()) != null) {
                if (answer.equals("0"))
                    System.exit(0);
                else crud(answer,reader);
                runNavigation();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("If you want to insert information about school press 1");
        System.out.println("If you want get information by teachers by day of week press 2");
        System.out.println("If you want to get information about teachers who does not work on this day press 3");
        System.out.println("If you want to get days by number of classes press 4");
        System.out.println("If you want to get days by number of busy audiences press 5");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String answer, BufferedReader reader) {
        switch (answer) {
            case "1" -> operateDataDB.insertDataToDB(reader);
            case "2" -> operateDataDB.getInformationAboutTeachersByDayOfWeek(reader);
            case "3" -> operateDataDB.getInformationAboutTeachersNotWorkDay(reader);
            case "4" -> operateDataDB.getDayOfWeekByNumberClasses(reader);
            case "5" -> operateDataDB.getDayOfWeekByNumberByNumberAudiences(reader);
        }
    }
}