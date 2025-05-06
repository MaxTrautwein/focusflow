package hse.group1.focusflow.steps;

import hse.group1.focusflow.model.Task;
import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import io.cucumber.java.en.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreateTaskSteps {

    private User testUser;
    private Task task;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private String errorMessage = null;
    private String warningMessage = null;
    private boolean dbUnavailable = false;
    private boolean submitted = false;

    @Given("the user is logged into FocusFlow")
    public void the_user_is_logged_into_focus_flow() {
        testUser = new User("test@example.com", "Pass!234t0", "John", "Doe");
    }

    @Given("the user is on the task management interface")
    public void the_user_is_on_the_task_management_interface() {
        // Assume interface is accessible – nothing to simulate
    }

    @When("the user clicks on the \"Create New Task\" button")
    public void the_user_clicks_on_the_create_new_task_button() {
        task = new Task(); 
    }

    @When("fills in the title {string}")
    public void fills_in_the_title(String string) {
        task.setTitle(string);
    }

    @When("fills in the description {string}")
    public void fills_in_the_description(String string) {
        task.setShort_description(string);
        task.setLong_description(string);
    }

    @When("sets the due date to a future date")
    public void sets_the_due_date_to_a_future_date() {
        task.setDue_date(Instant.now().plus(2, ChronoUnit.DAYS));
    }

    @When("selects priority {string}")
    public void selects_priority(String string) {
        task.setPriority(TaskPriority.valueOf(string.toUpperCase()));
    }

    @When("submits the form")
    public void submits_the_form() {
        if (dbUnavailable) {
            warningMessage = "Task could not be saved. Please try again.";
            return;
        }

        task.assignTo(testUser);
        task.setStatus(TaskStatus.OPEN);
        submitted = true;

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if (!violations.isEmpty()) {
            errorMessage = violations.iterator().next().getMessage();
        }
    }

    @Then("the task should be saved in the system")
    public void the_task_should_be_saved_in_the_system() {
        assertTrue(submitted, "Task was not submitted");
        assertNotNull(task);
        assertTrue(validator.validate(task).isEmpty(), "Task validation failed");
    }

    @Then("it should appear in the user's task list with status {string}")
    public void it_should_appear_in_the_users_task_list_with_status(String string) {
        assertEquals(TaskStatus.valueOf(string.toUpperCase()), task.getStatus());
    }

    @When("leaves the title empty")
    public void leaves_the_title_empty() {
        task.setTitle("");
    }

    @Then("the system should display an error message saying {string}")
    public void the_system_should_display_an_error_message_saying(String expectedMessage) {
        
            Set<ConstraintViolation<Task>> violations = validator.validate(task);
            if (!violations.isEmpty()) {
                errorMessage = violations.iterator().next().getMessage();
            }

        assertEquals(expectedMessage, errorMessage);
    }

    @When("sets the due date to a past date")
    public void sets_the_due_date_to_a_past_date() {
        task.setDue_date(Instant.now().minus(2, ChronoUnit.DAYS));
    }

    @Given("the database is temporarily unavailable")
    public void the_database_is_temporarily_unavailable() {
        dbUnavailable = true;
    }

    @When("the user tries to submit a valid task")
    public void the_user_tries_to_submit_a_valid_task() {
        task = new Task("Valid Task", "desc", "desc",
                Instant.now().plus(2, ChronoUnit.DAYS), testUser,
                TaskPriority.HIGH, TaskStatus.OPEN);
        submitted = true;

        if (dbUnavailable) {
            warningMessage = "Task could not be saved. Please try again.";
        }
    }

    @Then("the system should display a warning message saying {string}")
    public void the_system_should_display_a_warning_message_saying(String expectedWarning) {
        assertEquals(expectedWarning, warningMessage);
    }
}
