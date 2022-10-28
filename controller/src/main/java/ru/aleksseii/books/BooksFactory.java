package ru.aleksseii.books;

import org.jetbrains.annotations.NotNull;
import ru.aleksseii.Book;

import java.util.Collection;

public interface BooksFactory {

    @NotNull Collection<@NotNull Book> getBooks();
}
