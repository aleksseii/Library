package ru.aleksseii;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class Book {

    long id;

    String title;

    Author author;
}
