// src/main/java/mycompany/SpringPruebaMVC/model/services/ReportEntryServiceImpl.java

package mycompany.SpringPruebaMVC.model.services;

import mycompany.SpringPruebaMVC.model.entities.ReportEntry;
import mycompany.SpringPruebaMVC.model.entities.ActionType;
import mycompany.SpringPruebaMVC.model.repositories.ReportEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportEntryServiceImpl implements ReportEntryService {

    private final ReportEntryRepository reportEntryRepository;

    @Autowired
    public ReportEntryServiceImpl(ReportEntryRepository reportEntryRepository) {
        this.reportEntryRepository = reportEntryRepository;
    }

    @Override
    public void createReport(ActionType actionType, String description, Integer relatedEntityId) {
        ReportEntry report = new ReportEntry();
        report.setActionType(actionType);
        report.setDescription(description);
        report.setTimestamp(LocalDateTime.now());
        report.setRelatedEntityId(relatedEntityId);
        reportEntryRepository.save(report);
    }

    @Override
    public List<ReportEntry> getAllReports() {
        return reportEntryRepository.findAll();
    }

    @Override
    public List<ReportEntry> getReportsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return reportEntryRepository.findByTimestampBetween(start, end);
    }

    @Override
    public void deleteReport(Integer id) {
        reportEntryRepository.deleteById(id);
    }
}
