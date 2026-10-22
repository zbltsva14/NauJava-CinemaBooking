package ru.zyablitseva.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.Hall;

@Repository
@RepositoryRestResource(path = "halls")
public interface HallRepository extends CrudRepository<Hall, Long> {
}