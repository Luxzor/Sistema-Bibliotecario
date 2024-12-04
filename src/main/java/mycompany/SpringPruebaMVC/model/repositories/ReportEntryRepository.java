// src/main/java/mycompany/SpringPruebaMVC/model/repositories/ReportEntryRepository.java

package mycompany.SpringPruebaMVC.model.repositories;

import mycompany.SpringPruebaMVC.model.entities.ReportEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportEntryRepository extends JpaRepository<ReportEntry, Integer> {
    List<ReportEntry> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
