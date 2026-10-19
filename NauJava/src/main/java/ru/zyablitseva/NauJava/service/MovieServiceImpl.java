package ru.zyablitseva.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.repository.MovieRepository;

/**
 * Реализация бизнес-логики приложения для работы с фильмами.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void createMovie(Long id, String title, String genre) {
        Movie newMovie = new Movie();
        newMovie.setId(id);
        newMovie.setTitle(title);
        newMovie.setGenre(genre);
        movieRepository.create(newMovie);
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.delete(id);
    }

    @Override
    public void updateGenre(Long id, String newGenre) {
        Movie movie = movieRepository.read(id);
        if (movie != null) {
            movie.setGenre(newGenre);
            movieRepository.update(movie);
        }
    }
}