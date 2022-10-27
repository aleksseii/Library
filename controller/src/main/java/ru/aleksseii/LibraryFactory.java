package ru.aleksseii;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public final class LibraryFactory {

    private static long AMOUNT_OF_BOOKS = 0L;

    private static final String DELIMITER = " - ";

    public static Library createLibrary(@NotNull Path path) {

        Library library = new Library(new HashMap<>());
        try (InputStream inputStream = Files.newInputStream(path)) {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "Windows-1251")
            );

            String line;
            while ((line = reader.readLine()) != null) {

                String[] splitLine = line.split(DELIMITER);
                Book newBook = new Book(
                        ++AMOUNT_OF_BOOKS,
                        splitLine[1],
                        new Author(splitLine[0])
                );

                library.addBook(newBook);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return library;
    }
}
