package hse.group1.focusflow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void testUserCreation() {
    User user = new User("test@example.com", "securePassword", "John", "Doe");

    assertEquals("test@example.com", user.getEmail());
    assertEquals("John", user.getFirst_name());
    assertEquals("Doe", user.getLast_name());
    assertNotNull(user.getCreated_at(), "created_at should be initialized");

    user.setEmail("updated@example.com");
    assertEquals(
      "updated@example.com",
      user.getEmail(),
      "Email should be updated"
    );

    user.setFirst_name("Jane");
    assertEquals("Jane", user.getFirst_name(), "First name should be updated");

    user.setLast_name("Smith");
    assertEquals("Smith", user.getLast_name(), "Last name should be updated");
  }

  @Test
  public void testGetIdInitiallyNull() {
    User user = new User("id@check.com", "password", "First", "Last");
    assertNull(user.getId(), "ID should be null before persistence");
  }

  @Test
  public void testUpdateLastLogin() {
    User user = new User("some@user.com", "pw", "First", "Last");

    // Call the helper
    user.updateLast_login();

    //Veryfy the actuaL date of last_login 
    Instant referenzeTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    assertEquals(user.getLast_login().truncatedTo(ChronoUnit.SECONDS), referenzeTime, "Last login time and reference time are not matching" );

    //Check if DateTimeParseException is not thrown, mean's Date has right format
    assertDoesNotThrow(() -> { Instant instant = Instant.parse(String.valueOf(user.getLast_login()));}, "last_login should be a valid dateformat");

    // Now last_login should not be null
    assertNotNull(
      user.getLast_login(),
      "last_login should be set after update"
    );
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

  @Test
  public void testEmptyPassword(){
    User user = new User("set@pw.com", "initial", "Set", "");

    assertThrows(IllegalArgumentException.class, () -> user.setPassword("                      "), "Should throw IllegalArgumentException when setting empty password");
    assertThrows(IllegalArgumentException.class, () -> user.setPassword("         "), "Should throw IllegalArgumentException when setting empty password");
    assertThrows(IllegalArgumentException.class, () -> user.setPassword("   "), "Should throw IllegalArgumentException when setting empty password");
  }
}
