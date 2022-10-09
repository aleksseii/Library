package ru.aleksseii;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString
public class Library {

    private List<Book> books;

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Book> getBooksByAuthorName(String authorName) {

        return books.stream()
                .filter(b -> b.getAuthor().getName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    public void addBook(Book book) {
        books.add(book);
    }
}
