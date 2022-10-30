package ru.aleksseii.exceptions;


public final class NarrowCapacityException extends RuntimeException {

    public NarrowCapacityException() { // default constructor
    }

    public NarrowCapacityException(String message) {
        super(message);
    }
}
