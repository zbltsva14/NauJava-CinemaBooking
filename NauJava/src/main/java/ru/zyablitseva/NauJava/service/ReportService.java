package ru.zyablitseva.NauJava.service;

import ru.zyablitseva.NauJava.entity.Report;

/**
 * Сервис для работы с отчётами.
 */
public interface ReportService {

    /**
     * Создать отчёт в БД. Статус по умолчанию — CREATED.
     * @return id созданного отчёта
     */
    Long createReport();

    /**
     * Получить отчёт по id.
     */
    Report getReport(Long id);

    /**
     * Асинхронно запустить формирование отчёта.
     */
    void generateReportAsync(Long reportId);
}