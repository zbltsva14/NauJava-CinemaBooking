package ru.zyablitseva.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.Session;

import java.util.List;

@Repository
@RepositoryRestResource(path = "sessions")
public interface SessionRepository extends CrudRepository<Session, Long> {

    /**
     * Поиск сеансов по названию фильма (JPQL запрос через связанную сущность).
     */
    @Query("SELECT s FROM Session s WHERE s.movie.title = :movieTitle")
    List<Session> findByMovieTitle(@Param("movieTitle") String movieTitle);
}