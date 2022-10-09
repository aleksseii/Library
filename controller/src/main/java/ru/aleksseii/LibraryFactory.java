package ru.aleksseii;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LibraryFactory {

    private static long AMOUNT_OF_BOOKS = 0L;

    private static final String regex = " - ";

    public static Library createLibrary(@NotNull Path path) {

        List<Book> books = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(path)) {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "Windows-1251")
            );

            String line;
            while ((line = reader.readLine()) != null) {

                String[] splitLine = line.split(regex);
                Book newBook = Book.builder()
                        .id(++AMOUNT_OF_BOOKS)
                        .author(new Author(splitLine[0]))
                        .title(splitLine[1])
                        .build();
                books.add(newBook);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Library(books);
    }
}
