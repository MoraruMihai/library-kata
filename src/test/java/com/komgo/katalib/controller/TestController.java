package com.komgo.katalib.controller;

import com.komgo.katalib.exception.BookNotAvailableException;
import com.komgo.katalib.exception.BookNotFoundException;
import com.komgo.katalib.exception.NoActiveBorrowRecordException;
import com.komgo.katalib.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/user-not-found")
    public void throwUserNotFoundException() {
        throw new UserNotFoundException(1L);
    }

    @GetMapping("/book-not-found")
    public void throwBookNotFoundException() {
        throw new BookNotFoundException(1L);
    }

    @GetMapping("/book-not-available")
    public void throwBookNotAvailableException() {
        throw new BookNotAvailableException(1L);
    }

    @GetMapping("/no-active-borrow-record")
    public void throwNoActiveBorrowRecordException() {
        throw new NoActiveBorrowRecordException(1L, 1L);
    }
}