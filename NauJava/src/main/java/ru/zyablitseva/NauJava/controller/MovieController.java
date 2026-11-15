package ru.zyablitseva.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.entity.Session;
import ru.zyablitseva.NauJava.repository.MovieRepository;
import ru.zyablitseva.NauJava.repository.SessionRepository;

import java.util.List;

/**
 * REST-контроллер для работы с фильмами.
 */
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository, SessionRepository sessionRepository) {
        this.movieRepository = movieRepository;
        this.sessionRepository = sessionRepository;
    }

    /**
     * Поиск фильмов по жанру и рейтингу.
     */
    @GetMapping("/find")
    public List<Movie> findMovies(
            @RequestParam String genre,
            @RequestParam String ageRating) {
        return movieRepository.findByGenreAndAgeRating(genre, ageRating);
    }

    /**
     * Поиск сеансов по названию фильма.
     */
    @GetMapping("/sessions")
    public List<Session> findSessionsByMovie(@RequestParam String title) {
        return sessionRepository.findByMovieTitle(title);
    }
}