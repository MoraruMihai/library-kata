package com.komgo.katalib.controller;

import com.komgo.katalib.entity.Book;
import com.komgo.katalib.entity.BorrowRecord;
import com.komgo.katalib.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(libraryService.addBook(book));
    }

    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<BorrowRecord> borrowBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.borrowBook(userId, bookId));
    }

    @PostMapping("/return/{userId}/{bookId}")
    public ResponseEntity<Void> returnBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {
        libraryService.returnBook(userId, bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/borrowed/{userId}")
    public ResponseEntity<List<Book>> getBorrowedBooks(@PathVariable Long userId) {
        return ResponseEntity.ok(libraryService.getBorrowedBooks(userId));
    }
}