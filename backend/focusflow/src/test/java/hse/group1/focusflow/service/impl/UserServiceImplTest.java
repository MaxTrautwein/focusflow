package hse.group1.focusflow.service.impl;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserDto;
import hse.group1.focusflow.model.dto.UserLoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.model.dto.UserUpdateDto;
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
    void loginWithUnknownEmail() {
        UserLoginDto dto = new UserLoginDto();
        dto.setEmail("test@test.com");
        dto.setPassword("test12345!!");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userService.login(dto));

        assertEquals("Invalid credentials", exception.getReason());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void loginWithWrongPassword() {
        UserLoginDto dto = new UserLoginDto();
        dto.setEmail("test@test.com");
        dto.setPassword("test12345!!");

        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("...111aaAaa");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () ->userService.login(dto));

        assertEquals("Invalid credentials", exception.getReason());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void loginWithValidCredentials() {
        UserLoginDto dto = new UserLoginDto();
        dto.setEmail("test@test.com");
        dto.setPassword("test12345!!");

        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test12345!!");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        User retUser = userService.login(dto);

        verify(userRepository).save(argThat( user1 ->
                user1.getEmail().equals(user.getEmail())
                && user1.getFirstName().equals(user.getFirstName())
                && user1.getLastName().equals(user.getLastName())
                && user1.passwordMatches("test12345!!")
        ));

        assertNotNull(retUser);
        assertEquals(user.getEmail(), retUser.getEmail());
        assertEquals(user.getFirstName(), retUser.getFirstName());
        assertEquals(user.getLastName(),retUser.getLastName());
        assertTrue(retUser.passwordMatches("test12345!!"));
    }

    @Test
    void getUnknownProfile() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.getProfile("test@test.com"));

        assertEquals("User not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getExistingProfile() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test12345!!");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        UserDto dto = userService.getProfile("test@test.com");

        assertNotNull(dto);
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertNull(user.getTeam());
    }

    @Test
    void updateUnknownProfile() {
        UserUpdateDto dto = new UserUpdateDto();
        dto.setEmail("test@test.com");
        dto.setPassword("test12345!!");
        dto.setFirstName("Test");
        dto.setLastName("Test");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.updateProfile("test@test.com", dto));

        assertEquals("User not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void updateToTakenEmailProfile() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test12345!!");

        UserUpdateDto dto = new UserUpdateDto();
        dto.setEmail("test2@test.com");
        dto.setPassword("test12345!!");
        dto.setFirstName("Test");
        dto.setLastName("Test");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test2@test.com")).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.updateProfile("test@test.com", dto));

        assertEquals("Email already in use", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void updateFullProfile() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("test12345!!");

        UserUpdateDto dto = new UserUpdateDto();
        dto.setEmail("test2@test.com");
        dto.setPassword("TEST12345!!");
        dto.setFirstName("Test2");
        dto.setLastName("Test2");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("test2@test.com")).thenReturn(Optional.empty());

        UserDto updatedUser = userService.updateProfile("test@test.com", dto);

        verify(userRepository).save(argThat(user1 ->
                user1.getEmail().equals(dto.getEmail())
                && user1.getFirstName().equals(dto.getFirstName())
                && user1.getLastName().equals(dto.getLastName())
                && user1.passwordMatches(dto.getPassword())
        ));

        assertNotNull(updatedUser);
        assertEquals(dto.getEmail(), updatedUser.getEmail());
        assertEquals(dto.getFirstName(), updatedUser.getFirstName());
        assertEquals(dto.getLastName(), updatedUser.getLastName());
    }
}