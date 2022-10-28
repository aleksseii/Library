package ru.aleksseii;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.library.Library;
import ru.aleksseii.library.LibraryFactory;

public final class Application {

    public static void main(@NotNull String @NotNull [] args) {

        final Injector injector = Guice.createInjector(new LibraryModule(args[0]));
        final LibraryFactory libraryFactory = injector.getInstance(LibraryFactory.class);
        final Library library = libraryFactory.createLibrary(Integer.parseInt(args[1]));
        library.printContent(System.out);
    }
}
