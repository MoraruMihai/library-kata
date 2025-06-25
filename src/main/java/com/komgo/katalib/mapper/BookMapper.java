package com.komgo.katalib.mapper;

import com.komgo.katalib.dto.BookDTO;
import com.komgo.katalib.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setAvailable(book.isAvailable());
        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        return book;
    }
}