package com.komgo.katalib.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecordDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
}