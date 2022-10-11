package ru.aleksseii;

import lombok.*;

@AllArgsConstructor
@Data
public class Book {

    private long id;

    private String title;

    private Author author;
}
