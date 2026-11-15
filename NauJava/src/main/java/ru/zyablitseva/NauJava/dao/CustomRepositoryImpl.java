package ru.zyablitseva.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.entity.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация кастомных запросов.
 */
@Repository
public class CustomRepositoryImpl implements CustomRepository {

    private final EntityManager entityManager;

    @Autowired
    public CustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Movie> findMoviesByGenreAndAgeRating(String genre, String ageRating) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
        Root<Movie> movieRoot = cq.from(Movie.class);
        List<Predicate> predicates = new ArrayList<>();
        if (genre != null && !genre.isEmpty()) {
            predicates.add(cb.equal(movieRoot.get("genre"), genre));
        }
        if (ageRating != null && !ageRating.isEmpty()) {
            predicates.add(cb.equal(movieRoot.get("ageRating"), ageRating));
        }
        cq.select(movieRoot).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Session> findSessionsByMovieTitle(String movieTitle) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> cq = cb.createQuery(Session.class);
        Root<Session> sessionRoot = cq.from(Session.class);
        Join<Session, Movie> movieJoin = sessionRoot.join("movie", JoinType.INNER);
        Predicate titlePredicate = cb.equal(movieJoin.get("title"), movieTitle);
        cq.select(sessionRoot).where(titlePredicate);
        return entityManager.createQuery(cq).getResultList();
    }
}