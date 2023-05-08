package avlyakulov.timur.book.chapter_13.task_5.controller;

import avlyakulov.timur.book.chapter_13.task_5.crud.OperateDataSouvenirs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SouvenirsController {
    private OperateDataSouvenirs operateDataSouvenirs = new OperateDataSouvenirs();

    public void run() {
        String answer;
        runNavigation();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while ((answer = reader.readLine()) != null) {
                if (answer.equals("0"))
                    System.exit(0);
                else crud(answer, reader);
                runNavigation();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void crud(String answer, BufferedReader reader) {
        switch (answer) {
            case "1" -> operateDataSouvenirs.insertDataToDB(reader);
            case "2" -> operateDataSouvenirs.getInformationOfProductsByProducer(reader);
            case "3" -> operateDataSouvenirs.getInformationOfProductsByCountry(reader);
            case "4" -> operateDataSouvenirs.getProducersInformationByYear(reader);
            case "5" -> operateDataSouvenirs.deleteProducerAndHisProducts(reader);
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("If you want to insert data press 1");
        System.out.println("If you want to get information about products by producer name press 2");
        System.out.println("If you want to get information about products by country press 3");
        System.out.println("If you want to get information about producers by year of release press 4");
        System.out.println("If you want delete producer press 5");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }
}
