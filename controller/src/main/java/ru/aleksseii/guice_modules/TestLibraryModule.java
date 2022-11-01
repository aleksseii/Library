package ru.aleksseii.guice_modules;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.books.BooksFactory;
import ru.aleksseii.books.FileBooksFactory;

import java.util.Objects;

public final class TestLibraryModule extends AbstractModule {

    private final @NotNull String fileName =
            Objects.requireNonNull(this.getClass()
                            .getClassLoader()
                            .getResource("books.txt"))
                    .getPath();

    @Override
    protected void configure() {

        bind(BooksFactory.class).toInstance(new FileBooksFactory(fileName));
    }
}
