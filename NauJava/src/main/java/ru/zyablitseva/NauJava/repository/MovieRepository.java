package ru.zyablitseva.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyablitseva.NauJava.entity.Movie;

import java.util.List;

/**
 * Реализация слоя доступа к данным для сущности.
 */
@Component
public class MovieRepository implements CrudRepository<Movie, Long> {

    private final List<Movie> movieContainer;

    @Autowired
    public MovieRepository(List<Movie> movieContainer) {
        this.movieContainer = movieContainer;
    }

    @Override
    public void create(Movie movie) {
        movieContainer.add(movie);
    }

    @Override
    public Movie read(Long id) {
        for (Movie movie : movieContainer) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null; // Если не нашли
    }

    @Override
    public void update(Movie movie) {
        for (int i = 0; i < movieContainer.size(); i++) {
            if (movieContainer.get(i).getId().equals(movie.getId())) {
                movieContainer.set(i, movie);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        movieContainer.removeIf(movie -> movie.getId().equals(id));
    }
}