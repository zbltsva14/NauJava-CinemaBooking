package ru.zyablitseva.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}