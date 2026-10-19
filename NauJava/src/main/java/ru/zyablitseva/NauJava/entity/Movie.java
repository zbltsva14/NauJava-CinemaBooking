package ru.zyablitseva.NauJava.entity;

/**
 * Сущность, представляющая фильм в системе кинотеатра.
 * Содержит основную информацию, необходимую для отображения в списке фильмов.
 */
public class Movie {

    //Уникальный идентификатор фильма в базе данных
    private Long id;

    // Название фильма
    private String title;

    // Краткое описание сюжета
    private String description;

    // Длительность фильма в минутах
    private int durationMinutes;

    // Жанр фильма
    private String genre;

    // Возрастное ограничение
    private String ageRating;


    public Movie() {
    }

    public Movie(Long id, String title, String description, int durationMinutes, String genre, String ageRating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.genre = genre;
        this.ageRating = ageRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    /**
     * Возвращает строковое представление фильма.
     * @return строка с информацией о фильме
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", genre='" + genre + '\'' +
                ", ageRating='" + ageRating + '\'' +
                '}';
    }
}