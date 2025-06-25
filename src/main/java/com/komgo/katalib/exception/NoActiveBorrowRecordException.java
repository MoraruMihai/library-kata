package com.komgo.katalib.exception;

public class NoActiveBorrowRecordException extends RuntimeException {
    public NoActiveBorrowRecordException(Long userId, Long bookId) {
        super("No active borrow record found for user ID " + userId + " and book ID " + bookId);
    }
}