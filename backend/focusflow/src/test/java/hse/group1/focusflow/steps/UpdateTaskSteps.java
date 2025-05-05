package hse.group1.focusflow.steps;

import hse.group1.focusflow.model.Task;
import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateTaskSteps {

    private final Map<String, Task> taskStore = new HashMap<>();
    private final Map<String, User> userStore = new HashMap<>();
    private String lastError = null;
    private String lastWarning = null;
    private boolean dbConnectionAvailable = true;
    private String lastUpdatedTaskTitle = null;

    @Given("the following task exists for user {string}:")
    public void the_following_task_exists_for_user(String email, DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> taskData = rows.get(0);
        User user = userStore.computeIfAbsent(email, e -> new User(e, "First", "Last", "password"));

        Instant dueDate = switch (taskData.get("dueDate").toLowerCase()) {
            case "tomorrow" -> Instant.now().plus(1, ChronoUnit.DAYS);
            case "next week" -> Instant.now().plus(7, ChronoUnit.DAYS);
            default -> Instant.now().plus(2, ChronoUnit.DAYS);
        };

        Task task = new Task(
                taskData.get("title"),
                taskData.get("description"),
                taskData.get("description"),
                dueDate,
                user,
                TaskPriority.valueOf(taskData.get("priority")),
                TaskStatus.OPEN
        );

        taskStore.put(task.getTitle(), task);
    }

    @When("the user {string} updates the task {string} with:")
    public void the_user_updates_the_task_with(String userEmail, String taskTitle, DataTable dataTable) {
        lastError = null;
        lastWarning = null;

        if (!dbConnectionAvailable) {
            lastError = "Database not available";
            return;
        }

        Task task = taskStore.get(taskTitle);
        if (task == null) {
            lastWarning = "Task not found";
            return;
        }

        Map<String, String> updateData = dataTable.asMap(String.class, String.class);

        try {
            if (updateData.containsKey("title")) {
                String newTitle = updateData.get("title") == null ? "" : updateData.get("title").trim();
                if (newTitle.isEmpty()) {
                    lastError = "Title is required";
                    return;
                }
                taskStore.remove(task.getTitle());
                task.setTitle(newTitle);
                taskStore.put(newTitle, task);
            }
            if (updateData.containsKey("dueDate")) {
                Instant newDue = switch (updateData.get("dueDate").toLowerCase()) {
                    case "next week" -> Instant.now().plus(7, ChronoUnit.DAYS);
                    case "tomorrow" -> Instant.now().plus(1, ChronoUnit.DAYS);
                    default -> Instant.now().plus(2, ChronoUnit.DAYS);
                };
                task.setDue_date(newDue);
            }
            if (updateData.containsKey("priority")) {
                task.setPriority(TaskPriority.valueOf(updateData.get("priority")));
            }
            if (updateData.containsKey("description")) {
                task.setLong_description(updateData.get("description"));
            }

            lastUpdatedTaskTitle = task.getTitle();

        } catch (Exception e) {
            lastError = e.getMessage();
        }
    }

    @Then("the task {string} should be updated with:")
    public void the_task_should_be_updated_with(String taskTitle, DataTable dataTable) {
        Task task = taskStore.get(taskTitle);
        if (task == null && lastUpdatedTaskTitle != null) {
            task = taskStore.get(lastUpdatedTaskTitle);
        }

        assertNotNull(task, "Task should exist");

        Map<String, String> expected = dataTable.asMap(String.class, String.class);

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            switch (entry.getKey()) {
                case "dueDate" -> assertTrue(task.getDue_date().isAfter(Instant.now()));
                case "priority" -> assertEquals(TaskPriority.valueOf(entry.getValue()), task.getPriority());
                case "description" -> assertEquals(entry.getValue(), task.getLong_description());
            }
        }
    }

    @Then("the system should give an error: {string}")
    public void the_system_should_give_an_error(String expectedError) {
        assertEquals(expectedError, lastError);
    }

    @Given("the task {string} was deleted")
    public void the_task_was_deleted(String taskTitle) {
        taskStore.remove(taskTitle);
    }

    @When("the user {string} attempts to update the task with:")
    public void the_user_attempts_to_update_the_task_with(String userEmail, DataTable dataTable) {
        the_user_updates_the_task_with(userEmail, "Create prototype", dataTable);
    }

    @Then("the system should give a warning: {string}")
    public void the_system_should_give_a_warning(String message) {
        assertEquals(message, lastWarning);
    }

    @Given("the database has temporarily no connection")
    public void the_database_has_temporarily_no_connection() {
        dbConnectionAvailable = false;
    }

    @Then("the system should inform the user of the failure and allow a retry")
    public void the_system_should_inform_the_user_of_the_failure_and_allow_a_retry() {
        assertEquals("Database not available", lastError);
    }
}
