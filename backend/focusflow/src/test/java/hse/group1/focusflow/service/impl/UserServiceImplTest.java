package hse.group1.focusflow.service.impl;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.repository.TeamRepository;
import hse.group1.focusflow.repository.UserRepository;
import hse.group1.focusflow.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, teamRepository);
    }

    @AfterEach
    void tearDown() {
        userService = null;
    }

    @Test
    void registerNewUser() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("test@test.com");
        dto.setFirstName("Test");
        dto.setLastName("Test");
        dto.setPassword("test12345!!");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        userService.register(dto);

        verify(userRepository).save(argThat(user ->
                user.getEmail().equals(dto.getEmail())
                && user.getFirstName().equals(dto.getFirstName())
                && user.getLastName().equals(dto.getLastName())
                && user.passwordMatches(dto.getPassword())
        ));
    }

    @Test
    void registerDuplicateEmailUser() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("test@test.com");
        dto.setFirstName("Test");
        dto.setLastName("Test");
        dto.setPassword("test12345!!");

        when(userRepository.findByEmail("test@test.com")).thenReturn(
                Optional.of(new User("test@test.com", "test12345!!", "Test", "Test")));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userService.register(dto));

        assertEquals("Email already in use", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void registerUserWithUnknownTeam() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("test@test.com");
        dto.setFirstName("Test");
        dto.setLastName("Test");
        dto.setPassword("test12345!!");
        dto.setTeamId(123L);

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userService.register(dto));

        assertEquals("Team not found", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void registerUserWithTeam() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("test@test.com");
        dto.setFirstName("Test");
        dto.setLastName("Test");
        dto.setPassword("test12345!!");
        dto.setTeamId(123L);

        Team team = new Team();
        team.setName("Team");


        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(teamRepository.findById(123L)).thenReturn(Optional.of(team));

        userService.register(dto);

        verify(userRepository).save(argThat(user ->
                user.getEmail().equals(dto.getEmail())
                        && user.getFirstName().equals(dto.getFirstName())
                        && user.getLastName().equals(dto.getLastName())
                        && user.passwordMatches(dto.getPassword())
                        && user.getTeam().equals(team)
        ));
    }

    @Test
    void login() {
    }

    @Test
    void getProfile() {
    }

    @Test
    void updateProfile() {
    }
}