package com.komgo.katalib.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(Long bookId) {
        super("Book with ID " + bookId + " is currently not available for borrowing");
    }
}