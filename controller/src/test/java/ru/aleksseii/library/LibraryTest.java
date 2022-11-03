package ru.aleksseii.library;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.aleksseii.Author;
import ru.aleksseii.Book;
import ru.aleksseii.books.BooksFactory;
import ru.aleksseii.exceptions.EmptyBookCellException;
import ru.aleksseii.exceptions.NarrowCapacityException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public final class LibraryTest {

    private static final int NARROW_CAPACITY = 1;

    private static final int SUITED_CAPACITY = 5;
    private static final int TOO_LARGE_CAPACITY = 50;

    private static @NotNull List<Book> bookList = List.of();
    @Mock
    private @NotNull BooksFactory booksFactory;

    @BeforeAll
    public static void fulfillBookList() {
        bookList = List.of(
                new Book(new Author("Author 0"), "Book 0"),
                new Book(new Author("Author 1"), "Book 1"),
                new Book(new Author("Author 2"), "Book 2"),
                new Book(new Author("Author 3"), "Book 3"),
                new Book(new Author("Author 4"), "Book 4")
        );
    }

    @BeforeEach
    public void initBooksFactory() {

        booksFactory = mock(BooksFactory.class);
        when(booksFactory.getBooks()).thenReturn(bookList);
    }

    @Test
    @DisplayName("Constructor of Library should throw `NarrowCapacityException` " +
            "when amount of provided books is less than provided capacity")
    void shouldThrowExceptionIfCapacityIsLessThanSize() {

        assertThrows(NarrowCapacityException.class, () -> new Library(NARROW_CAPACITY, booksFactory));
    }

    @Test
    @DisplayName("Created library should contain provided books in same order as " +
            "factory returned it, other cells should be empty")
    void shouldContainInExactOrder() {

        boolean testPassed = true;
        Library library = new Library(TOO_LARGE_CAPACITY, booksFactory);

        // checking if filled cells contain books
        // in exactly the same order as source list of books
        for (int i = 0; i < bookList.size(); i++) {
            testPassed &= bookList.get(i).equals(library.extractBook(i));
        }
        assertTrue(testPassed);

        // checking if all the rest cells are empty
        for (int i = bookList.size(); i < library.getCapacity(); i++) {
            testPassed &= !library.cellIsFilled(i);
        }
        
        assertTrue(testPassed);
    }

    @Test
    @DisplayName("Info about book and book cell should be " +
            "printed into provided `PrintStream` after extracting book from library")
    void shouldPrintInfoAfterExtractingBook() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outputStream));

        Library library = new Library(TOO_LARGE_CAPACITY, booksFactory);
        String expectedString = "From position [%d], extracted book %s\n";

        int cellPosition = 0;
        Book extractedBook = library.extractBook(cellPosition);
        String extractedBookJson = new Gson().toJson(extractedBook);
        assertEquals(
                String.format(expectedString, cellPosition, extractedBookJson),
                outputStream.toString()
        );

        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Library should throw `EmptyBookCellException` if someone tries to extract book from empty book cell")
    void shouldThrowExceptionIfTryingToExtractBookFromEmptyCell() {

        Library library = new Library(TOO_LARGE_CAPACITY, booksFactory);
        int indexOutOfRange = library.getSize() + 1;
        assertThrows(EmptyBookCellException.class, () -> library.extractBook(indexOutOfRange));
    }

    @Test
    @DisplayName("Library should return the book, locating exactly in the provided cell position")
    void shouldReturnCorrectBook() {

        Library library = new Library(SUITED_CAPACITY, booksFactory);
        int cellPosition = 0;
        Book expectedBook = bookList.get(cellPosition);

        assertEquals(expectedBook, library.extractBook(cellPosition));
    }

    @Test
    @DisplayName("Library should add book exactly into first found empty cell book")
    void shouldAddBookExactlyIntoFirstEmptyCell() {

        Library library = new Library(SUITED_CAPACITY, booksFactory);
        int cellPos1 = 2;
        int cellPos2 = 4;

        // thus 2-nd and 4-th positions are vacant
        library.extractBook(cellPos1);
        library.extractBook(cellPos2);
        assertFalse(library.cellIsFilled(cellPos1));
        assertFalse(library.cellIsFilled(cellPos2));

        //noinspection ConstantConditions
        int newBookExpectedPosition = Math.min(cellPos1, cellPos2);

        Book newBook = new Book(new Author("Brand new Author"), "Brand new book");
        int addedBookPosition = library.addBook(newBook);

        assertEquals(newBook, library.extractBook(newBookExpectedPosition));
        assertEquals(newBookExpectedPosition, addedBookPosition);
    }

    @Test
    @DisplayName("Library should throw `NarrowCapacityException` if " +
            "trying to add new book and there is no any empty cell for it")
    void shouldThrowExceptionIfTryingToAddBookAndThereIsNoEmptyCells() {

        Library library = new Library(SUITED_CAPACITY, booksFactory);
        assertEquals(library.getSize(), library.getCapacity());

        Book newBook = new Book(new Author("Brand new Author"), "Brand new book");
        assertThrows(NarrowCapacityException.class, () -> library.addBook(newBook));
    }

    @Test
    @DisplayName("Library should print its content if invoked `printContent` method")
    void shouldPrintContent() {

        Library library = new Library(TOO_LARGE_CAPACITY, booksFactory);
        assertEquals(
                getExpectedContent(library.getCapacity()),
                getRealContent(library)
        );
    }

    private @NotNull String getExpectedContent(int libraryCapacity) {

        StringBuilder result = new StringBuilder();
        Gson gson = new Gson();

        IntStream.range(0, bookList.size())
                .mapToObj(i -> String.format("[%d]\t\t", i) + gson.toJson(bookList.get(i)) + "\r\n")
                .forEach(result::append);

        IntStream.range(bookList.size(), libraryCapacity)
                .mapToObj(i -> String.format("[%d]\t\t{}\r\n", i))
                .forEach(result::append);

        return result.toString();
    }

    private @NotNull String getRealContent(@NotNull Library library) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outputStream));
        library.printContent(System.out);

        System.setOut(originalOut);

        return outputStream.toString();
    }
}
