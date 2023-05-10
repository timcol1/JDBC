package avlyakulov.timur.book.chapter_13.task_11.controller;

import avlyakulov.timur.book.chapter_13.task_11.crud.OperatePlanetDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GalaxyController {
    OperatePlanetDB operatePlanetController = new OperatePlanetDB();

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
        System.out.println("if you want get all planets from this galaxy where is life press 2");
        System.out.println("if you want get galaxy with max temperature of core planets press 3");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String answer, BufferedReader reader) {
        switch (answer) {
            case "1" -> operatePlanetController.insertDataToDB(reader);
            case "2" -> operatePlanetController.getInformationAboutPlanetByGalaxy(reader);
            case "3" -> operatePlanetController.findGalaxyMaxCoreTemperature();
        }
    }
}
