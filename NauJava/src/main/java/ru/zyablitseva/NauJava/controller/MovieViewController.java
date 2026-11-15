package ru.zyablitseva.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zyablitseva.NauJava.repository.MovieRepository;

/**
 * WEB-контроллер для работы с HTML.
 */
@Controller
@RequestMapping("/view/movies")
public class MovieViewController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieViewController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Отображает страницу со списком всех фильмов.
     */
    @GetMapping("/list")
    public String showMovieList(Model model) {
        // Передаем список фильмов в HTML-шаблон
        model.addAttribute("movies", movieRepository.findAll());
        return "movies";
    }
}