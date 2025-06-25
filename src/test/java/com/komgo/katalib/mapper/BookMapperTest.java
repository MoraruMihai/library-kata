package com.komgo.katalib.mapper;

import com.komgo.katalib.dto.BookDTO;
import com.komgo.katalib.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    void testToDto() {
        // Setup entity
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");

        // Map to DTO
        BookDTO dto = bookMapper.toDto(book);

        // Assertions
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getTitle()).isEqualTo("Effective Java");
        assertThat(dto.getAuthor()).isEqualTo("Joshua Bloch");
    }

}