package avlyakulov.timur.book.chapter_13.task_8.controller;

import avlyakulov.timur.book.chapter_13.task_8.crud.OperateToForecastDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeatherController {
    OperateToForecastDB operateToForecastDB = new OperateToForecastDB();

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
        System.out.println("If you want to insert data to db press 1");
        System.out.println("If you want get weather by region name press 2");
        System.out.println("if you want to get information about weather by language for last week press 3");
        System.out.println("If you want to get average temperature in regions for last week press 4");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String answer, BufferedReader reader) {
        switch (answer) {
            case "1" -> operateToForecastDB.insetDataToDB(reader);
            case "2" -> operateToForecastDB.getWeatherByRegion(reader);
            case "3" -> operateToForecastDB.getWeatherByLanguageForLastWeek(reader);
            case "4" -> operateToForecastDB.getAverageWeatherForLastWeek();
        }
    }
}
