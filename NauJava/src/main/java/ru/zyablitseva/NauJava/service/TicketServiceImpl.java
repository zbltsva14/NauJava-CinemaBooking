package ru.zyablitseva.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.zyablitseva.NauJava.entity.Session;
import ru.zyablitseva.NauJava.entity.Ticket;
import ru.zyablitseva.NauJava.repository.SessionRepository;
import ru.zyablitseva.NauJava.repository.TicketRepository;

/**
 * Реализация бизнес-логики для работы с билетами.
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             SessionRepository sessionRepository,
                             PlatformTransactionManager transactionManager) {
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void buyTicket(Long sessionId, int seatNumber, int rowNumber) {
        // Начинаем транзакцию
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            //Ищем сеанс
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new RuntimeException("Сеанс не найден!"));

            //Создаем билет
            Ticket ticket = new Ticket();
            ticket.setSession(session);
            ticket.setSeatNumber(seatNumber);
            ticket.setRowNumber(rowNumber);
            ticket.setBooked(true);

            //Сохраняем билет
            ticketRepository.save(ticket);

            // Имитация ошибки для проверки отката (раскомментируй, чтобы проверить)
            //if (seatNumber == 0) throw new RuntimeException("Ошибка при оплате!");

            transactionManager.commit(status);
            System.out.println("Билет успешно куплен! Место: " + rowNumber + " ряд, " + seatNumber + " место.");

        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("Ошибка при покупке билета. Транзакция отменена: " + e.getMessage());
            throw e;
        }
    }
}