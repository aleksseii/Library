package ru.aleksseii.books;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.Book;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Collection;

public final class FileBooksFactory implements BooksFactory {

    private static final @NotNull Type BOOK_COLLECTION_TYPE =
            new TypeToken<Collection<Book>>() {}.getType();

    private final @NotNull String fileName;

    @Inject
    public FileBooksFactory(@NotNull String fileName) {
        this.fileName = fileName;
    }

    @Override
    public @NotNull Collection<@NotNull Book> getBooks() {

        Gson gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            return gson.fromJson(reader, BOOK_COLLECTION_TYPE);

        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
