package org.gopnik.repository;

import org.gopnik.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    @Query("SELECT i FROM EventLog i WHERE i.drugstoreId = :drugstoreId AND i.timestamp >= :fromDate AND i.timestamp <= :toDate ")
    public List<EventLog> findByTimestampBetween(@Param("drugstoreId") Long drugstoreId, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("SELECT MIN(FUNCTION('DATE', i.timestamp)) FROM EventLog i WHERE i.drugstoreId = :drugstoreId")
    public LocalDate findEarliestDate(@Param("drugstoreId") Long drugstoreId);
}
