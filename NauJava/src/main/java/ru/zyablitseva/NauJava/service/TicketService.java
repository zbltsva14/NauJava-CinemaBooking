package ru.zyablitseva.NauJava.service;

/**
 * Интерфейс бизнес-логики для работы с билетами.
 */
public interface TicketService {

    /**
     * Покупка билета.
     * @param sessionId id сеанса
     * @param seatNumber номер места
     * @param rowNumber номер ряда
     */
    void buyTicket(Long sessionId, int seatNumber, int rowNumber);
}