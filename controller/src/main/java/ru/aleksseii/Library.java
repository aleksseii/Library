package ru.aleksseii;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@ToString
public final class Library {

    private Map<Author, List<Book>> authorToBooks;

    public List<Book> getAllBooks() {

        return authorToBooks.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthorName(String authorName) {

        return authorToBooks.get(new Author(authorName));
    }

    public void addBook(Book book) {

        Author author = book.getAuthor();

        List<Book> books = authorToBooks.containsKey(author) ? authorToBooks.get(author) : new ArrayList<>();
        books.add(book);
        authorToBooks.put(author, books);
    }
}
