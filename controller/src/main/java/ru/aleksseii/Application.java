package ru.aleksseii;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private static final Path libraryDataPath = Paths.get("controller/src/main/resources/authors_and_books.txt");

    public static void main(String[] args) {

        Library library = LibraryFactory.createLibrary(libraryDataPath);

        library.getAllBooks().forEach(System.out::println);
    }

    private static void testLibrary() {
        List<Book> books = new ArrayList<>();

        Author firstAuthor = new Author("first");
        for (long i = 0; i < 10; i++) {
            books.add(new Book(i, "title" + i, firstAuthor));
        }

        Author secondAuthor = new Author("second");
        for (long i = 10; i < 20; i++) {
            books.add(new Book(i, "title" + i, secondAuthor));
        }

        Library library = new Library(books);
        library.getBooksByAuthorName("first").forEach(System.out::println);
    }
}
