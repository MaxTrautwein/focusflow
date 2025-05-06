package hse.group1.focusflow.steps;

import hse.group1.focusflow.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserRegistrationSteps {

    private User user;
    private Validator validator;
    private Set<ConstraintViolation<User>> violations;
    private final Map<String, User> userStore = new HashMap<>();

    @Given("the user is on the registration page")
    public void user_is_on_registration_page() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @When("the user fills out the registration form")
    public void user_fills_out_form() {
        user = new User("maxmustermann@example.com", "password123", "Max", "Mustermann");

        violations = validator.validate(user);

        if (violations.isEmpty()) {
            userStore.put(user.getEmail(), user);
        }
    }

    @Then("the user should be registered successfully")
    public void user_is_registered() {
        assertNotNull(user, "User object should exist");
        assertTrue(violations.isEmpty(), "There should be no validation errors");
    
        User storedUser = userStore.get(user.getEmail());
        assertNotNull(storedUser, "User should be saved in the user store");
        assertEquals(user.getEmail(), storedUser.getEmail(), "Email addresses should match");
    
        assertTrue(user.passwordMatches("password123"), "The password should be correctly hashed");
    }    
}
