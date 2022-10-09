package ru.aleksseii;

import com.google.gson.Gson;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Path libraryDataPath = Paths.get("controller/src/main/resources/authors_and_books.txt");

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final Gson GSON = new Gson();

    public static void main(String[] args) {

        Library library = LibraryFactory.createLibrary(libraryDataPath);

        String authorName;

        while (true) {

            System.out.println("Введите имя автора на кириллице в формате `{имя} {фамилия}`. " +
                    "Для выхода введите `выход`:");

            authorName = SCANNER.nextLine();
            if (authorName.equalsIgnoreCase("выход")) {
                break;
            }

            List<Book> books = library.getBooksByAuthorName(authorName);
            books.stream()
                    .map(GSON::toJson)
                    .toList()
                    .forEach(System.out::println);
            System.out.println();
        }
    }
}
