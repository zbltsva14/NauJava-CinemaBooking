package ru.zyablitseva.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.Hall;

@Repository
public interface HallRepository extends CrudRepository<Hall, Long> {
}