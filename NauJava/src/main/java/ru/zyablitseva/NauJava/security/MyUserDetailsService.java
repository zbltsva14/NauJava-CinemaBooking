package ru.zyablitseva.NauJava.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.zyablitseva.NauJava.entity.User;
import ru.zyablitseva.NauJava.repository.UserRepository;

import java.util.List;

/**
 * Преобразует пользователя приложения в UserDetails для Spring Security.
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User appUser = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + login));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name())
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(appUser.getLogin())
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
    }
}