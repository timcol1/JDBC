package avlyakulov.timur.book.chapter_13.task_2.controller;

import avlyakulov.timur.book.chapter_13.task_2.crud.GetDataFromDB;
import avlyakulov.timur.book.chapter_13.task_2.crud.InsertDataToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VideoLibraryController {
    private final GetDataFromDB getDataFromDB = new GetDataFromDB();

    public void run() {
        String position;
        runNavigation();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((position = reader.readLine()) != null) {
                if (position.equals("0"))
                    System.exit(0);
                else crud(position, reader);
                System.out.println();
                runNavigation();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("If you want to insert data to tables press 1");
        System.out.println("If you want to find all films for this year and past press 2");
        System.out.println("if you want to get name of film and number actors in this film press 3");
        System.out.println("If you want to find information of film by number of actors in this film press 4");
        System.out.println("If you want to delete film by number of years press 5");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> insertDataToTable(reader);
            case "2" -> getFilmsByYear();
            case "3" -> getNameFilmNumbersActors(reader);
            case "4" -> getInformationAboutFilmBYNumberOfActors(reader);
            case "5" -> deleteFilm(reader);
        }
    }

    private void insertDataToTable(BufferedReader reader) {
        new InsertDataToDB().insertToTableData(reader);
    }

    private void getFilmsByYear() {
        getDataFromDB.findAllFilmsByYear();
    }

    private void getNameFilmNumbersActors(BufferedReader reader) {
        getDataFromDB.findActorsByFilm(reader);
    }
    private void getInformationAboutFilmBYNumberOfActors (BufferedReader reader) {
        getDataFromDB.findFilmsByNumberOfActors(reader);
    }
    private void deleteFilm (BufferedReader reader) {
        getDataFromDB.deleteFilmsByDate(reader);
    }
}