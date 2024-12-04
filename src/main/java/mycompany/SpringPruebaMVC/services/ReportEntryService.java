// src/main/java/mycompany/SpringPruebaMVC/model/services/ReportEntryService.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.ReportEntry;
import mycompany.SpringPruebaMVC.model.entities.ActionType;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportEntryService {
    void createReport(ActionType actionType, String description, Integer relatedEntityId);
    List<ReportEntry> getAllReports();
    List<ReportEntry> getReportsBetweenDates(LocalDateTime start, LocalDateTime end);
    void deleteReport(Integer id);
}
