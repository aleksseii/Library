package ru.aleksseii.exceptions;

public final class EmptyBookCellException extends RuntimeException {

    public EmptyBookCellException() { // default constructor
    }

    public EmptyBookCellException(String message) {
        super(message);
    }
}
