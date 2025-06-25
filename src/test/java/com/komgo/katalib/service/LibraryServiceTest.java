package com.komgo.katalib.service;

import com.komgo.katalib.entity.Book;
import com.komgo.katalib.entity.BorrowRecord;
import com.komgo.katalib.entity.User;
import com.komgo.katalib.exception.BookNotAvailableException;
import com.komgo.katalib.exception.BookNotFoundException;
import com.komgo.katalib.exception.UserNotFoundException;
import com.komgo.katalib.repository.BookRepository;
import com.komgo.katalib.repository.BorrowRecordRepository;
import com.komgo.katalib.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    private LibraryService libraryService;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowRecordRepository borrowRecordRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        userRepository = mock(UserRepository.class);
        borrowRecordRepository = mock(BorrowRecordRepository.class);
        libraryService = new LibraryService(bookRepository, userRepository, borrowRecordRepository);
    }

    @Test
    void testAddBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = libraryService.addBook(book);

        assertEquals("Test Book", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testBorrowBookSuccess() {
        Long userId = 1L;
        Long bookId = 1L;

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(true);

        BorrowRecord borrowRecord = new BorrowRecord();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecord result = libraryService.borrowBook(userId, bookId);

        assertNotNull(result);
        assertFalse(book.isAvailable());
        verify(bookRepository, times(1)).save(book);
        verify(borrowRecordRepository, times(1)).save(any(BorrowRecord.class));
    }

    @Test
    void testBorrowBookUserNotFound() {
        Long userId = 1L;
        Long bookId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> libraryService.borrowBook(userId, bookId));

        assertEquals("User with ID 1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(bookRepository, borrowRecordRepository);
    }

    @Test
    void testBorrowBookBookNotFound() {
        Long userId = 1L;
        Long bookId = 2L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> libraryService.borrowBook(userId, bookId));

        assertEquals("Book with ID 2 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(bookRepository, times(1)).findById(bookId);
        verifyNoInteractions(borrowRecordRepository);
    }

    @Test
    void testBorrowBookBookNotAvailable() {
        Long userId = 1L;
        Long bookId = 3L;

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(BookNotAvailableException.class, () -> libraryService.borrowBook(userId, bookId));

        assertEquals("Book with ID 3 is currently not available for borrowing", exception.getMessage());
        verify(bookRepository, times(0)).save(book);
        verifyNoInteractions(borrowRecordRepository);
    }

    @Test
    void testReturnBookSuccess() {
        Long userId = 1L;
        Long bookId = 1L;

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(1L);
        borrowRecord.setBook(book);
        borrowRecord.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowRecordRepository.findByUserAndReturnedAtIsNull(user))
                .thenReturn(List.of(borrowRecord));

        assertDoesNotThrow(() -> libraryService.returnBook(userId, bookId));

        assertTrue(book.isAvailable());
        verify(bookRepository, times(1)).save(book);
        verify(borrowRecordRepository, times(1)).save(borrowRecord);
    }

    @Test
    void testGetBorrowedBooks() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Borrowed Book");

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(borrowRecordRepository.findByUserAndReturnedAtIsNull(user)).thenReturn(List.of(borrowRecord));

        List<Book> borrowedBooks = libraryService.getBorrowedBooks(userId);

        assertEquals(1, borrowedBooks.size());
        assertEquals("Borrowed Book", borrowedBooks.get(0).getTitle());
        verify(userRepository, times(1)).findById(userId);
        verify(borrowRecordRepository, times(1)).findByUserAndReturnedAtIsNull(user);
    }
}