package ru.zyablitseva.NauJava.dao;

import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.entity.Session;

import java.util.List;

/**
 * Интерфейс для кастомных запросов.
 */
public interface CustomRepository {

    List<Movie> findMoviesByGenreAndAgeRating(String genre, String ageRating);

    List<Session> findSessionsByMovieTitle(String movieTitle);
}