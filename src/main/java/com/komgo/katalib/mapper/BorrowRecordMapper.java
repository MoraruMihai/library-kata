package com.komgo.katalib.mapper;

import com.komgo.katalib.dto.BorrowRecordDTO;
import com.komgo.katalib.entity.BorrowRecord;
import org.springframework.stereotype.Component;

@Component
public class BorrowRecordMapper {

    public BorrowRecordDTO toDto(BorrowRecord borrowRecord) {
        BorrowRecordDTO dto = new BorrowRecordDTO();
        dto.setId(borrowRecord.getId());
        dto.setBookId(borrowRecord.getBook().getId());
        dto.setUserId(borrowRecord.getUser().getId());
        dto.setBorrowedAt(borrowRecord.getBorrowedAt());
        dto.setReturnedAt(borrowRecord.getReturnedAt());
        return dto;
    }
}