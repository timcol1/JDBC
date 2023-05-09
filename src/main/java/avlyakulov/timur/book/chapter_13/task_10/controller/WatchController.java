package avlyakulov.timur.book.chapter_13.task_10.controller;

import avlyakulov.timur.book.chapter_13.task_10.crud.OperateToWatchStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WatchController {
    OperateToWatchStore operateToWatchStore = new OperateToWatchStore();

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
        System.out.println("If you want insert data to db press 1");
        System.out.println("If you want to get watch by type of watch press 2");
        System.out.println("If you want ot get watch by country release press 3");
        System.out.println("If you want get producers by number of watch at warehouse press 4");
        System.out.println("If you want to finish program press 0");
        System.out.println();
    }

    private void crud(String answer, BufferedReader reader) {
        switch (answer) {
            case "1" -> operateToWatchStore.insertDataToDb(reader);
            case "2" -> operateToWatchStore.getBrandWatchByType(reader);
            case "3" -> operateToWatchStore.getWatchByCountryRelease(reader);
            case "4" -> operateToWatchStore.getProducersByQuantityAtStore(reader);
        }
    }
}
