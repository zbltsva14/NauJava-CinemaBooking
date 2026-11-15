package ru.zyablitseva.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zyablitseva.NauJava.entity.Report;
import ru.zyablitseva.NauJava.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Создать отчёт и запустить асинхронное формирование.
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createReport() {
        Long id = reportService.createReport();
        reportService.generateReportAsync(id);
        return ResponseEntity.ok(id);
    }

    /**
     * Получить отчёт по id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        switch (report.getStatus()) {
            case CREATED:
                return ResponseEntity.ok("Отчёт ещё не сформирован (статус: CREATED)");
            case ERROR:
                return ResponseEntity.ok("Ошибка при формировании отчёта: " + report.getContent());
            case COMPLETED:
                return ResponseEntity.ok()
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .body(report.getContent());
            default:
                return ResponseEntity.ok("Неизвестный статус");
        }
    }
}