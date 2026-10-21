package ru.zyablitseva.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.zyablitseva.NauJava.dao.CustomRepository;
import ru.zyablitseva.NauJava.entity.Hall;
import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.entity.Session;
import ru.zyablitseva.NauJava.repository.HallRepository;
import ru.zyablitseva.NauJava.repository.MovieRepository;
import ru.zyablitseva.NauJava.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class RepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CustomRepository customRepository;

    /**
     * Тестирование кастомного метода репозитория (Query Lookup Strategy).
     */
    @Test
    void testFindMoviesByGenreAndAgeRating() {
        String genre = "Фантастика" + UUID.randomUUID();
        String ageRating = "16+";
        Movie movie = new Movie();
        movie.setTitle("Тестовый фильм");
        movie.setGenre(genre);
        movie.setAgeRating(ageRating);
        movieRepository.save(movie);

        List<Movie> foundMovies = movieRepository.findByGenreAndAgeRating(genre, ageRating);

        Assertions.assertFalse(foundMovies.isEmpty());
        Assertions.assertEquals(movie.getId(), foundMovies.getFirst().getId());
    }

    /**
     * Тестирование кастомного метода репозитория (JPQL Query).
     */
    @Test
    void testFindSessionsByMovieTitle() {
        String movieTitle = "фильм " + UUID.randomUUID();
        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movieRepository.save(movie);

        Hall hall = new Hall();
        hall.setName("Зал 1");
        hallRepository.save(hall);

        Session session = new Session();
        session.setMovie(movie);
        session.setHall(hall);
        session.setStartTime(LocalDateTime.now());
        session.setPrice(500.0);
        sessionRepository.save(session);

        List<Session> foundSessions = sessionRepository.findByMovieTitle(movieTitle);

        Assertions.assertFalse(foundSessions.isEmpty());
        Assertions.assertEquals(session.getId(), foundSessions.getFirst().getId());
    }

    /**
     * Тестирование метода с Criteria API.
     */
    @Test
    void testFindMoviesByCriteriaApi() {
        String genre = "Комедия" + UUID.randomUUID();
        String ageRating = "12+";
        Movie movie = new Movie();
        movie.setTitle("Смешной фильм");
        movie.setGenre(genre);
        movie.setAgeRating(ageRating);
        movieRepository.save(movie);

        List<Movie> foundMovies = customRepository.findMoviesByGenreAndAgeRating(genre, ageRating);

        Assertions.assertFalse(foundMovies.isEmpty());
        Assertions.assertEquals(movie.getId(), foundMovies.getFirst().getId());
    }
}