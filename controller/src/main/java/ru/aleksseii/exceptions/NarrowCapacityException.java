package ru.aleksseii.exceptions;


public final class NarrowCapacityException extends RuntimeException {

    public NarrowCapacityException() {
    }

    public NarrowCapacityException(String message) {
        super(message);
    }
}
