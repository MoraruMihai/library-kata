package com.komgo.katalib.repository;

import com.komgo.katalib.entity.BorrowRecord;
import com.komgo.katalib.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUserAndReturnedAtIsNull(User user);
}