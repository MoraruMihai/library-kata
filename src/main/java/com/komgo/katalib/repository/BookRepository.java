package com.komgo.katalib.repository;

import com.komgo.katalib.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}