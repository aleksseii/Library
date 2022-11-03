package ru.aleksseii.library;

import com.google.gson.Gson;
import com.google.inject.Inject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.aleksseii.Book;
import ru.aleksseii.BookCell;
import ru.aleksseii.books.BooksFactory;
import ru.aleksseii.exceptions.EmptyBookCellException;
import ru.aleksseii.exceptions.NarrowCapacityException;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Getter
public final class Library {

    private static final @NotNull Gson GSON = new Gson();

    private final @NotNull BookCell @NotNull [] bookCells;

    private int size;

    @Inject
    public Library(int capacity, @NotNull BooksFactory factory) {

        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }

        Collection<@NotNull Book> bookCollection = factory.getBooks();
        if (capacity < bookCollection.size()) {
            throw new NarrowCapacityException(
                    "Capacity of the library is less than amount of provided books"
            );
        }

        this.size = bookCollection.size();
        this.bookCells = new BookCell[capacity];
        for (int i = 0; i < this.bookCells.length; i++) {
            this.bookCells[i] = new BookCell();
        }

        int i = 0;
        for (Book book : bookCollection) {
            this.bookCells[i++].putBook(book);
        }
    }

    public Library(@NotNull BookCell @NotNull [] bookCells) {

        this.bookCells = bookCells;
        this.size = (int) Arrays.stream(bookCells)
                .filter(BookCell::isFilled)
                .count();
    }

    public int getCapacity() {

        return bookCells.length;
    }

    public boolean cellIsFilled(int cellPosition) {

        checkCellPosition(cellPosition);
        return bookCells[cellPosition].isFilled();
    }

    /**
     * @param book new book to add in library
     * @return index of cell, new book has been inserted into
     */
    public int addBook(@NotNull Book book) {

        int emptyCellIndex = findEmptyCell();
        if (emptyCellIndex == -1) {
            throw new NarrowCapacityException("There is no empty cell for another book");
        }

        this.size++;
        bookCells[emptyCellIndex].putBook(book);

        return emptyCellIndex;
    }

    /**
     * @param cellPosition number of cell to extract book from
     * @return book, extracted from the pointed cell, `null` if cell is empty
     */
    public @NotNull Book extractBook(int cellPosition) {

        if (!cellIsFilled(cellPosition)) {
            throw new EmptyBookCellException("Illegal to extract book from empty book cell");
        }

        Book extractedBook = bookCells[cellPosition].extractBook();
        String extractedBookJson = GSON.toJson(extractedBook);
        System.out.format("From position [%d], extracted book %s\n",
                cellPosition, extractedBookJson);

        this.size--;
        assert extractedBook != null;
        return extractedBook;
    }

    public @NotNull List<Book> getAllBooks() {
        return Arrays.stream(bookCells)
                .map(BookCell::getBook)
                .filter(Objects::nonNull)
                .toList();
    }

    public void printContent(@NotNull PrintStream stream) {

        IntStream.range(0, bookCells.length)
                .mapToObj(i -> String.format("[%d]\t\t", i) +
                        (bookCells[i].isFilled() ? GSON.toJson(bookCells[i].getBook()) : "{}"))
                .forEach(stream::println);
    }

    /**
     * @return position of first empty cell,
     * `-1` if there is no any empty cell
     */
    private int findEmptyCell() {

        for (int i = 0; i < bookCells.length; i++) {
            if (!bookCells[i].isFilled()) {
                return i;
            }
        }
        return -1;
    }

    private void checkCellPosition(int cellPosition) {

        if (cellPosition < 0 || cellPosition >= this.getCapacity()) {
            throw new IllegalArgumentException("Illegal cell position provided: " + cellPosition);
        }
    }
}
