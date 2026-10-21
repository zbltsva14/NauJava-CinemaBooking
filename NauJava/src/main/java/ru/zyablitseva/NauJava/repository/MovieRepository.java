package ru.zyablitseva.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    /**
     * Поиск фильмов по жанру и возрастному рейтингу.
     */
    List<Movie> findByGenreAndAgeRating(String genre, String ageRating);
}