package ru.zyablitseva.NauJava.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.zyablitseva.NauJava.entity.Role;
import ru.zyablitseva.NauJava.entity.User;
import ru.zyablitseva.NauJava.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    //findByLogin
    @Test
    void findByLogin_UserExists_ReturnsUser() {
        User user = new User();
        user.setLogin("ivan");
        when(userRepository.findByLogin("ivan")).thenReturn(Optional.of(user));
        User found = userService.findByLogin("ivan");
        assertNotNull(found);
        assertEquals("ivan", found.getLogin());
        verify(userRepository, times(1)).findByLogin("ivan");
    }

    @Test
    void findByLogin_UserNotFound_ReturnsNull() {
        when(userRepository.findByLogin("nobody")).thenReturn(Optional.empty());
        User found = userService.findByLogin("nobody");
        assertNull(found);
    }

    //addUser
    @Test
    void addUser_NewUser_SavesWithEncodedPassword() {
        User newUser = new User();
        newUser.setLogin("petr");
        newUser.setPassword("plain");
        when(userRepository.findByLogin("petr")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plain")).thenReturn("ENCODED");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        User saved = userService.addUser(newUser);
        assertNotNull(saved);
        assertEquals("ENCODED", saved.getPassword());
        assertEquals(Role.USER, saved.getRole());
        verify(passwordEncoder, times(1)).encode("plain");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void addUser_UserAlreadyExists_ThrowsException() {
        User existing = new User();
        existing.setLogin("ivan");
        when(userRepository.findByLogin("ivan")).thenReturn(Optional.of(existing));
        User newUser = new User();
        newUser.setLogin("ivan");
        newUser.setPassword("plain");
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.addUser(newUser));
        assertEquals("Пользователь с таким логином уже существует", ex.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
}