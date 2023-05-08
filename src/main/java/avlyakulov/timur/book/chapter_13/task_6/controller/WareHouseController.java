package avlyakulov.timur.book.chapter_13.task_6.controller;

import avlyakulov.timur.book.chapter_13.task_6.crud.OperateToWarehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WareHouseController {
    OperateToWarehouse operateToWarehouse = new OperateToWarehouse();
    public void run() {
        String answer;
        runNavigation();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while((answer = reader.readLine()) != null) {
                if (answer.equals("0"))
                    System.exit(0);
                else crud(answer, reader);
                runNavigation();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("If you want to insert data press 1");
        System.out.println("If you want to print information about orders press 2");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String answer, BufferedReader reader) {
        switch (answer) {
            case "1" -> operateToWarehouse.insertDataToDB(reader);
            case "2" -> operateToWarehouse.getInfoAboutOrders();
            //case "3" -> ;
        }
    }
}