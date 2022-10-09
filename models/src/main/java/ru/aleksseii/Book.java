package ru.aleksseii;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Book {

    private long id;

    private String title;

    private Author author;
}
