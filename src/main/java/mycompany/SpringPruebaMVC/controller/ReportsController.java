package Controller;

import Model_Entities.ReportEntry;
import Model_Services.ReportEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    private final ReportEntryService reportEntryService;

    @Autowired
    public ReportsController(ReportEntryService reportEntryService) {
        this.reportEntryService = reportEntryService;
    }

    // Mostrar lista de reportes
    @GetMapping
    public String listReports(@RequestParam(value = "startDate", required = false) String startDateStr,
                              @RequestParam(value = "endDate", required = false) String endDateStr,
                              Model model) {
        List<ReportEntry> reports;

        if (startDateStr != null && endDateStr != null) {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
            reports = reportEntryService.getReportsBetweenDates(startDateTime, endDateTime);
            model.addAttribute("startDate", startDateStr);
            model.addAttribute("endDate", endDateStr);
        } else {
            reports = reportEntryService.getAllReports();
        }

        model.addAttribute("reports", reports);
        return "reports/list";
    }

    // Eliminar un reporte
    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            reportEntryService.deleteReport(id);
            redirectAttributes.addFlashAttribute("message", "Reporte eliminado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el reporte: " + e.getMessage());
        }
        return "redirect:/reports";
    }
}
