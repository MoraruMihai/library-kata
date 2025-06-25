package com.komgo.katalib.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Book book;
    
    @ManyToOne
    @JsonBackReference
    private User user;
    
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    
    @PrePersist
    public void prePersist() {
        borrowedAt = LocalDateTime.now();
    }
}