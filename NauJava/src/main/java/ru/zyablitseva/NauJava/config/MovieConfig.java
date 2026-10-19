package ru.zyablitseva.NauJava.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.zyablitseva.NauJava.entity.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс конфигурации для работы с данными.
 * Содержит настройки бинов, имитирующих базу данных.
 */
@Configuration
public class MovieConfig {

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Movie> movieContainer() {
        return new ArrayList<>();
    }
}