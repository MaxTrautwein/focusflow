package hse.group1.focusflow.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import hse.group1.focusflow.model.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskStatusSteps {

    private User testUser;
    private Map<String, Task> taskMap = new HashMap<>();
    private String lastError = null;
    private String lastWarning = null;
    private boolean dbUnavailable = false;
    private boolean retrySuggested = false;
    private final List<String> overdueWarnings = new ArrayList<>();

    @Given("the following tasks exist for user {string}:")
    public void the_following_tasks_exist_for_user(String email, DataTable dataTable) {
        testUser = new User(email, "Test", "User", "pass123&A6d");

        for (Map<String, String> row : dataTable.asMaps()) {
            String title = row.get("title");
            String status = row.get("status");
            String dueDate = row.get("dueDate");

            Instant due;
            switch (dueDate.toLowerCase()) {
                case "today":
                    due = Instant.now();
                    break;
                case "yesterday":
                    due = Instant.now().minus(1, ChronoUnit.DAYS);
                    break;
                case "last week":
                    due = Instant.now().minus(7, ChronoUnit.DAYS);
                    break;
                default:
                    due = Instant.parse(dueDate);
            }

            TaskStatus taskStatus = TaskStatus.valueOf(status);
            Task task = new Task(title, "short", "long", due, testUser, TaskPriority.MEDIUM, taskStatus);
            taskMap.put(title, task);
        }
    }

    @When("the user {string} updates the task {string} to {string}")
    public void the_user_updates_the_task_to(String email, String taskTitle, String newStatus) {
        if (dbUnavailable) {
            retrySuggested = true;
            return;
        }

        Task task = taskMap.get(taskTitle);
        try {
            TaskStatus status = TaskStatus.valueOf(newStatus);
            if (task == null) {
                lastWarning = "Task not found";
            } else {
                task.setStatus(status);
            }
        } catch (IllegalArgumentException e) {
            lastError = "Invalid task status: " + newStatus;
        }
    }

    @Then("the task {string} should have status {string}")
    public void the_task_should_have_status(String taskTitle, String expectedStatus) {
        Task task = taskMap.get(taskTitle);
        assertNotNull(task, "Task should exist");
        assertEquals(TaskStatus.valueOf(expectedStatus), task.getStatus());
    }

    @When("the system performs the overdue status check")
    public void the_system_performs_the_overdue_status_check() {
        for (Task task : taskMap.values()) {
            if (task.isOverdue()) {
                overdueWarnings.add("Task '" + task.getTitle() + "' is overdue");
            }
        }
    }

    @Then("the task {string} should be considered overdue")
    public void the_task_should_be_considered_overdue(String taskTitle) {
        Task task = taskMap.get(taskTitle);
        assertNotNull(task, "Task should exist");
        assertTrue(task.isOverdue(), "Expected task '" + taskTitle + "' to be overdue");
    }

    @Then("the system should show an error: {string}")
    public void the_system_should_show_an_error(String expectedError) {
        assertEquals(expectedError, lastError);
    }

    @Then("the system should show a warning: {string}")
    public void the_system_should_show_a_warning(String expectedWarning) {
        if (lastWarning != null) {
            assertEquals(expectedWarning, lastWarning);
        } else {
            assertTrue(overdueWarnings.contains(expectedWarning), "Expected overdue warning: " + expectedWarning);
        }
    }

    @Given("the database is temporarily not available")
    public void the_database_is_temporarily_not_available() {
        dbUnavailable = true;
    }

    @Then("the system should show a retry option to the user")
    public void the_system_should_show_a_retry_option_to_the_user() {
        assertTrue(retrySuggested, "Expected retry option to be suggested");
    }
}
