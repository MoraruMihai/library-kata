package com.komgo.katalib.mapper;

import com.komgo.katalib.dto.BorrowRecordDTO;
import com.komgo.katalib.entity.Book;
import com.komgo.katalib.entity.BorrowRecord;
import com.komgo.katalib.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BorrowRecordMapperTest {

    private BorrowRecordMapper borrowRecordMapper;

    @BeforeEach
    void setUp() {
        borrowRecordMapper = new BorrowRecordMapper();
    }

    @Test
    void testToDto() {
        // Setup entity
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        User user = new User();
        user.setId(2L);
        user.setName("John Doe");

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(3L);
        borrowRecord.setBook(book);
        borrowRecord.setUser(user);
        borrowRecord.setBorrowedAt(LocalDateTime.of(2025, 6, 1, 10, 0));
        borrowRecord.setReturnedAt(LocalDateTime.of(2025, 6, 15, 15, 30));

        // Map to DTO
        BorrowRecordDTO dto = borrowRecordMapper.toDto(borrowRecord);

        // Assertions
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(3L);
        assertThat(dto.getBookId()).isEqualTo(1L);
        assertThat(dto.getUserId()).isEqualTo(2L);
        assertThat(dto.getBorrowedAt()).isEqualTo(LocalDateTime.of(2025, 6, 1, 10, 0));
        assertThat(dto.getReturnedAt()).isEqualTo(LocalDateTime.of(2025, 6, 15, 15, 30));
    }

}