package ru.zyablitseva.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}