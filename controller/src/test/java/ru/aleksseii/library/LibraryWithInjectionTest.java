package ru.aleksseii.library;

import com.google.inject.Inject;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aleksseii.books.BooksFactory;
import ru.aleksseii.exceptions.NarrowCapacityException;
import ru.aleksseii.guice_modules.TestLibraryModule;


@IncludeModule(TestLibraryModule.class)
public final class LibraryWithInjectionTest {

    private static final int NARROW_CAPACITY = 1;

    @Inject
    private BooksFactory booksFactory;

    @Test
    @DisplayName("Constructor of Library should throw `NarrowCapacityException` " +
            "when amount of provided books is less than provided capacity")
    void shouldThrowExceptionIfCapacityIsLessThanSize() {

        Assertions.assertThrows(NarrowCapacityException.class, () -> new Library(NARROW_CAPACITY, booksFactory));
    }
}
