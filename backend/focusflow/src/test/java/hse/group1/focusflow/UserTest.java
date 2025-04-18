package hse.group1.focusflow;

import static org.junit.jupiter.api.Assertions.*;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void testUserCreation() {
    User user = new User("test@example.com", "securePassword", "John", "Doe");

    assertEquals("test@example.com", user.getEmail());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertNotNull(user.getCreatedAt(), "createdAt should be initialized");

    user.setEmail("updated@example.com");
    assertEquals(
      "updated@example.com",
      user.getEmail(),
      "Email should be updated"
    );

    user.setFirstName("Jane");
    assertEquals("Jane", user.getFirstName(), "First name should be updated");

    user.setLastName("Smith");
    assertEquals("Smith", user.getLastName(), "Last name should be updated");
  }

  @Test
  public void testGetUserIdInitiallyNull() {
    User user = new User("id@check.com", "password", "First", "Last");
    assertNull(user.getUserId(), "userId should be null before persistence");
  }

  @Test
  public void testUpdateLastLogin() {
    User user = new User("some@user.com", "pw", "First", "Last");
    assertNull(user.getLastLogin(), "Initially lastLogin should be null");

    // Call the helper
    user.updateLastLogin();

    // Now lastLogin should not be null
    assertNotNull(user.getLastLogin(), "lastLogin should be set after update");
  }

  @Test
  public void testSetTeam() {
    User user = new User("team@user.com", "password", "Team", "User");
    Team team = new Team("Team Name", "Team Description");

    assertNull(user.getTeam(), "User should initially have no team");

    // Assign the team
    user.setTeam(team);

    // Check if relationship is set
    assertEquals(team, user.getTeam(), "Team should be assigned to the user");
  }

  private static Validator validator;

  @BeforeAll
  public static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testEmailNotNull() {
    // Create a User with a null email
    User user = new User(null, "password", "John", "Doe");
    Set<ConstraintViolation<User>> violations = validator.validate(user);

    // Expect at least one violation because email is null
    assertFalse(
      violations.isEmpty(),
      "Expected constraint violations when email is null"
    );

    // Check that one of the violations is specifically about email being null
    boolean hasNotNullViolation = violations
      .stream()
      .anyMatch(
        v ->
          v.getPropertyPath().toString().equals("email") &&
          v.getMessage().contains("cannot be null")
      );
    assertTrue(hasNotNullViolation, "Expected a NotNull violation on email");
  }

  @Test
  public void testInvalidEmailFormat() {
    // Create a User with an invalid email format
    User user = new User("invalid-email", "password", "John", "Doe");
    Set<ConstraintViolation<User>> violations = validator.validate(user);

    // Expect a violation for invalid email format
    assertFalse(
      violations.isEmpty(),
      "Expected constraint violations when email format is invalid"
    );

    boolean hasEmailFormatViolation = violations
      .stream()
      .anyMatch(
        v ->
          v.getPropertyPath().toString().equals("email") &&
          v.getMessage().contains("valid")
      );
    assertTrue(hasEmailFormatViolation, "Expected an email format violation");
  }

  @Test
  public void testPasswordHashing() {
    User user = new User("a@b.com", "123456", "A", "B");

    // The plain text "123456" should match the hashed password
    assertTrue(user.passwordMatches("123456"), "Correct password should match");

    // A wrong password must not match
    assertFalse(user.passwordMatches("wrong"), "Wrong password should fail");
  }

  @Test
  public void testSetPassword() {
    User user = new User("set@pw.com", "initial", "Set", "PW");
    // Change password
    user.setPassword("newSecret");

    // Must match new password
    assertTrue(
      user.passwordMatches("newSecret"),
      "Should match the new password"
    );

    // Must not match the old password
    assertFalse(user.passwordMatches("initial"), "Old password should fail");
  }
}
