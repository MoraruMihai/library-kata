package com.komgo.katalib.service;

import com.komgo.katalib.dto.BookDTO;
import com.komgo.katalib.dto.BorrowRecordDTO;
import com.komgo.katalib.entity.Book;
import com.komgo.katalib.entity.BorrowRecord;
import com.komgo.katalib.entity.User;
import com.komgo.katalib.exception.BookNotAvailableException;
import com.komgo.katalib.exception.BookNotFoundException;
import com.komgo.katalib.exception.NoActiveBorrowRecordException;
import com.komgo.katalib.exception.UserNotFoundException;
import com.komgo.katalib.mapper.BookMapper;
import com.komgo.katalib.mapper.BorrowRecordMapper;
import com.komgo.katalib.repository.BookRepository;
import com.komgo.katalib.repository.BorrowRecordRepository;
import com.komgo.katalib.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookMapper bookMapper;
    private final BorrowRecordMapper borrowRecordMapper;

    public BookDTO addBook(BookDTO book) {
        Book newBook = bookMapper.toEntity(book);
        return bookMapper.toDto(bookRepository.save(newBook));
    }

    @Transactional
    public BorrowRecordDTO borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException(bookId);
        }

        book.setAvailable(false);
        bookRepository.save(book);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setUser(user);

        return borrowRecordMapper.toDto(borrowRecordRepository.save(borrowRecord));
    }

    @Transactional
    public void returnBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        BorrowRecord borrowRecord = borrowRecordRepository.findByUserAndReturnedAtIsNull(user).stream()
                .filter(r -> r.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new NoActiveBorrowRecordException(userId, bookId));

        borrowRecord.setReturnedAt(LocalDateTime.now());
        borrowRecordRepository.save(borrowRecord);

        book.setAvailable(true);
        bookRepository.save(book);
    }

    public List<BookDTO> getBorrowedBooks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return borrowRecordRepository.findByUserAndReturnedAtIsNull(user).stream()
                .map(BorrowRecord::getBook)
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordDTO> getAllBorrowRecords() {
        List<BorrowRecord> records = borrowRecordRepository.findAll();
        return records.stream().map(borrowRecordMapper::toDto).collect(Collectors.toList());
    }
}