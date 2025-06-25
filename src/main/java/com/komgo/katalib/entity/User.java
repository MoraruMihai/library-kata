package com.komgo.katalib.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "library_user") // 'user' is a reserved keyword in some DBs
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<BorrowRecord> borrowedBooks = new ArrayList<>();
}