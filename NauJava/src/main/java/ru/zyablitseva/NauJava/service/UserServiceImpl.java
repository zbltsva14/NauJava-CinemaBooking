package ru.zyablitseva.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zyablitseva.NauJava.entity.Role;
import ru.zyablitseva.NauJava.entity.User;
import ru.zyablitseva.NauJava.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    @Override
    public User addUser(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        return userRepository.save(user);
    }
}