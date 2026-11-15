package ru.zyablitseva.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.entity.Report;
import ru.zyablitseva.NauJava.entity.ReportStatus;
import ru.zyablitseva.NauJava.repository.MovieRepository;
import ru.zyablitseva.NauJava.repository.ReportRepository;
import ru.zyablitseva.NauJava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             UserRepository userRepository,
                             MovieRepository movieRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public void generateReportAsync(Long reportId) {
        CompletableFuture.runAsync(() -> generateReport(reportId));
    }

    private void generateReport(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report == null) {
            return;
        }

        long totalStart = System.currentTimeMillis();

        AtomicLong userCount = new AtomicLong(0);
        AtomicLong usersTime = new AtomicLong(0);
        AtomicLong moviesTime = new AtomicLong(0);

        // Поток 1 — подсчёт пользователей
        Thread usersThread = new Thread(() -> {
            long start = System.currentTimeMillis();
            userCount.set(userRepository.count());
            usersTime.set(System.currentTimeMillis() - start);
        });

        // Поток 2 — получение списка фильмов
        final List<Movie>[] moviesHolder = new List[1];
        Thread moviesThread = new Thread(() -> {
            long start = System.currentTimeMillis();
            moviesHolder[0] = (List<Movie>) movieRepository.findAll();
            moviesTime.set(System.currentTimeMillis() - start);
        });

        try {
            usersThread.start();
            moviesThread.start();

            usersThread.join();
            moviesThread.join();

            String html = buildHtml(userCount.get(), usersTime.get(), moviesHolder[0], moviesTime.get(),
                    System.currentTimeMillis() - totalStart);

            report.setContent(html);
            report.setStatus(ReportStatus.COMPLETED);
            reportRepository.save(report);

        } catch (Exception e) {
            report.setStatus(ReportStatus.ERROR);
            report.setContent("Ошибка формирования отчёта: " + e.getMessage());
            reportRepository.save(report);
        }
    }

    private String buildHtml(long userCount, long usersTime, List<Movie> movies, long moviesTime, long totalTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Отчёт</title></head><body>");
        sb.append("<h1>Статистика приложения</h1>");

        sb.append("<h2>Общая информация</h2>");
        sb.append("<table border=\"1\" cellpadding=\"5\">");
        sb.append("<tr><th>Показатель</th><th>Значение</th><th>Время, мс</th></tr>");
        sb.append("<tr><td>Пользователей в системе</td><td>").append(userCount).append("</td><td>").append(usersTime).append("</td></tr>");
        sb.append("<tr><td>Фильмов в системе</td><td>").append(movies.size()).append("</td><td>").append(moviesTime).append("</td></tr>");
        sb.append("<tr><td colspan=\"2\">Общее время формирования</td><td>").append(totalTime).append("</td></tr>");
        sb.append("</table>");

        sb.append("<h2>Список фильмов</h2>");
        sb.append("<table border=\"1\" cellpadding=\"5\">");
        sb.append("<tr><th>ID</th><th>Название</th><th>Жанр</th><th>Длительность (мин)</th><th>Рейтинг</th></tr>");
        for (Movie m : movies) {
            sb.append("<tr><td>").append(m.getId()).append("</td>")
                    .append("<td>").append(escape(m.getTitle())).append("</td>")
                    .append("<td>").append(escape(m.getGenre())).append("</td>")
                    .append("<td>").append(m.getDurationMinutes()).append("</td>")
                    .append("<td>").append(escape(m.getAgeRating())).append("</td></tr>");
        }
        sb.append("</table>");

        sb.append("</body></html>");
        return sb.toString();
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}