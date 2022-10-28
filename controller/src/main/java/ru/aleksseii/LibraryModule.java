package ru.aleksseii;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.books.BooksFactory;
import ru.aleksseii.books.FileBooksFactory;
import ru.aleksseii.library.LibraryFactory;

public final class LibraryModule extends AbstractModule {

    private final @NotNull String fileName;

    public LibraryModule(@NotNull String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected void configure() {

        binder().requireExplicitBindings();

        bind(BooksFactory.class).toInstance(new FileBooksFactory(fileName));

        bind(LibraryFactory.class).toInstance(
                new LibraryFactory(new FileBooksFactory(fileName))
        );
    }
}
