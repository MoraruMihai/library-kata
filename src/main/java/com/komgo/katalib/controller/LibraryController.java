package com.komgo.katalib.controller;

import com.komgo.katalib.dto.BookDTO;
import com.komgo.katalib.dto.BorrowRecordDTO;
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
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO book) {
        return ResponseEntity.ok(libraryService.addBook(book));
    }

    @PostMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<BorrowRecordDTO> borrowBook(
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
    public ResponseEntity<List<BookDTO>> getBorrowedBooks(@PathVariable Long userId) {
        return ResponseEntity.ok(libraryService.getBorrowedBooks(userId));
    }
}