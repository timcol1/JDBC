package avlyakulov.timur.book.chapter_13.task_15.crud;

import avlyakulov.timur.book.chapter_13.conn.ConnectionToDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

//Словари. В двух различных базах данных хранятся два словаря: русско-
//английский. Клиент вводит слово и выбирает язык.
//Вывести перевод этого слова.
public class OperateWordsToDB {

    private String SQL_INSERT_ENG_WORD = "insert into dictionary.english_word (eng_word) values (?)";
    private String SQL_INSERT_UKR_WORD = "insert into dictionary.ukrainian_word (ukr_word) values (?)";
    private String SQL_INSERT_TRANSLATION = "insert into dictionary.translations (id_word_ukr,id_word_eng) values (?,?)";
    private String SQL_GET_TRANSLATIONS = "select ukr_word, eng_word\n" +
            "    from dictionary.translations\n" +
            "inner join dictionary.english_word on english_word.id_word = translations.id_word_eng\n" +
            "inner join dictionary.ukrainian_word on ukrainian_word.id_word = translations.id_word_ukr\n";

    public void insertTranslationToDB(BufferedReader reader) {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement insertEngWord = connection.prepareStatement(SQL_INSERT_ENG_WORD);
             PreparedStatement insertUkrWord = connection.prepareStatement(SQL_INSERT_UKR_WORD);
             PreparedStatement insertTranslation = connection.prepareStatement(SQL_INSERT_TRANSLATION)) {
            System.out.println("Choose action to do\n1.Enter ukr-eng translation\n2.Enter eng-ukr translation\n3.Enter translation to exist word");
            String answer = reader.readLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the ukrainian word");
                    String ukrWord = reader.readLine();
                    insertUkrWord.setString(1, ukrWord);
                    System.out.println("Enter his english translation");
                    String engWord = reader.readLine();
                    insertEngWord.setString(1, engWord);
                    insertUkrWord.executeUpdate();
                    insertEngWord.executeUpdate();
                    Statement statement = connection.createStatement();
                    ResultSet idWordUkrRes = statement.executeQuery("select id_word from dictionary.ukrainian_word " +
                            "where ukr_word = '" + ukrWord + "'");
                    idWordUkrRes.next();
                    int idUkrWord = idWordUkrRes.getInt(1);
                    insertTranslation.setInt(1, idUkrWord);
                    ResultSet idWordEngRes = statement.executeQuery("select id_word from dictionary.english_word " +
                            "where eng_word = '" + engWord + "'");
                    idWordEngRes.next();
                    int idEngWord = idWordEngRes.getInt(1);
                    insertTranslation.setInt(2, idEngWord);
                    insertTranslation.executeUpdate();
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getTranslations() {
        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement getTranslations = connection.prepareStatement(SQL_GET_TRANSLATIONS)) {
            ResultSet translations = getTranslations.executeQuery();
            System.out.println("List of word and translations");
            while (translations.next())
                System.out.printf("%d. %s - %s\n", translations.getRow(), translations.getString(1), translations.getString(2));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}