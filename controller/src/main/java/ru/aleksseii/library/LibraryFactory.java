package ru.aleksseii.library;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.books.BooksFactory;

public final class LibraryFactory {

    private final @NotNull BooksFactory booksFactory;

    @Inject
    public LibraryFactory(@NotNull BooksFactory booksFactory) {
        this.booksFactory = booksFactory;
    }

    public @NotNull Library createLibrary(int capacity) {

        return new Library(capacity, booksFactory);
    }
}
