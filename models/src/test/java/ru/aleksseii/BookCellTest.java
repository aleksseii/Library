package ru.aleksseii;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class BookCellTest {


    private final @NotNull Book book = new Book(new Author("Author 0"), "Book 0");

    @Test
    void shouldCreateEmptyCell() {

        BookCell emptyCell = new BookCell();
        assertFalse(emptyCell.isFilled());
        assertNull(emptyCell.getBook());
    }

    @Test
    void shouldCreateCellWithBook() {

        BookCell bookCell = new BookCell(book);

        assertTrue(bookCell.isFilled());
        assertNotNull(bookCell.getBook());
        assertEquals(book, bookCell.getBook());
    }

    @Test
    void shouldPutBookIntoCell() {

        BookCell emptyCell = new BookCell();
        emptyCell.putBook(book);

        assertTrue(emptyCell.isFilled());
        assertNotNull(emptyCell.getBook());
        assertEquals(book, emptyCell.getBook());
    }

    @Test
    void shouldExtractBookFromCell() {

        BookCell bookCell = new BookCell(book);
        bookCell.extractBook();

        assertFalse(bookCell.isFilled());
        assertNull(bookCell.getBook());
    }
}