package hse.group1.focusflow.repository;

import hse.group1.focusflow.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    User user1;
    User user2;


    @BeforeEach
    void setUp() {
        User _user1 = new User();
        _user1.setFirstName("John");
        _user1.setLastName("Doe");
        _user1.setEmail("john.doe@example.com");
        _user1.setPassword("password1A!");
        user1 = entityManager.persistAndFlush(_user1);

        User _user2 = new User();
        _user2.setFirstName("Jane");
        _user2.setLastName("Doe");
        _user2.setEmail("jane.doe@example.com");
        _user2.setPassword("password1B!");
        user2 = entityManager.persistAndFlush(_user2);
    }

    @AfterEach
    void tearDown() {
        entityManager.getEntityManager().createQuery("delete from User").executeUpdate();
    }

    @Test
    void findByEmailExists() {
        Optional<User> user = userRepository.findByEmail("john.doe@example.com");
        assertTrue(user.isPresent());
        User foundUser = user.get();
        assertEquals("John", foundUser.getFirstName());
        assertEquals("Doe", foundUser.getLastName());
        assertTrue(foundUser.passwordMatches("password1A!"));
    }

    @Test
    void findByEmailUnknown() {
        Optional<User> user = userRepository.findByEmail("john.doe1111@example.com");
        assertFalse(user.isPresent());
    }

    @Test
    void findByIdExists() {
        Optional<User> user = userRepository.findById(user2.getUserId());
        assertTrue(user.isPresent());
        User foundUser = user.get();
        assertEquals("Jane", foundUser.getFirstName());
        assertEquals("Doe", foundUser.getLastName());
        assertEquals("jane.doe@example.com", foundUser.getEmail());
        assertTrue(foundUser.passwordMatches("password1B!"));
    }

    @Test
    void findByIdUnknown() {
        // With 2 Users the addition of their ID's should Never Exist
        // In such a cleaned environment
        Optional<User> user = userRepository.findById(user2.getUserId() + user1.getUserId());
        assertFalse(user.isPresent());
    }

    @Test
    void createUser() {
        User user = new User();
        user.setFirstName("New");
        user.setLastName("User");
        user.setEmail("New.User@example.com");
        user.setPassword("New.Pa1word");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserId());

        User userEntity = entityManager.find(User.class, savedUser.getUserId());
        assertNotNull(userEntity);
        assertEquals("New", userEntity.getFirstName());
        assertEquals("User", userEntity.getLastName());
        assertEquals("New.User@example.com", userEntity.getEmail());
        assertTrue(userEntity.passwordMatches("New.Pa1word"));
    }

    @Test
    void removeUser() {
        userRepository.deleteById(user1.getUserId());

        User userEntity = entityManager.find(User.class, user1.getUserId());
        assertNull(userEntity);
    }

    @Test
    void updateUser() {
        long userId = user1.getUserId();
        Optional<User> user = userRepository.findById(userId);
        assertTrue(user.isPresent());
        User foundUser = user.get();
        foundUser.setFirstName("NewFirst");
        foundUser.setLastName("NewLast");
        foundUser.setEmail("New.User-mail@example.com");
        foundUser.setPassword("New.P1aaaaa");
        userRepository.save(foundUser);

        User updatedUser = entityManager.find(User.class, userId);
        assertNotNull(updatedUser);
        assertEquals("NewFirst", updatedUser.getFirstName());
        assertEquals("NewLast", updatedUser.getLastName());
        assertEquals("New.User-mail@example.com", updatedUser.getEmail());
        assertTrue(updatedUser.passwordMatches("New.P1aaaaa"));
    }
    

}