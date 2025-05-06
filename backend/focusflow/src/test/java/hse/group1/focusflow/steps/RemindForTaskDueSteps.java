package hse.group1.focusflow.steps;

import io.cucumber.java.en.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import hse.group1.focusflow.model.Task;
import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;
import io.cucumber.datatable.DataTable;

import static org.junit.jupiter.api.Assertions.*;

public class RemindForTaskDueSteps {

    private User testUser;
    private List<Task> tasks = new ArrayList<>();
    private List<Task> reminderTasks = new ArrayList<>();
    private boolean notificationsDisabled = false;

    @Given("the user {string} is assigned to the following tasks:")
    public void the_user_is_assigned_to_the_following_tasks(String email, DataTable dataTable) {
        testUser = new User(email, "Test", "User", "pas12A6ord");

        for (Map<String, String> row : dataTable.asMaps()) {
            String title = row.get("title");
            String dueDate = row.get("dueDate");
            String status = row.get("status");

            Instant dueInstant;
            switch (dueDate.toLowerCase()) {
                case "today":
                    dueInstant = Instant.now();
                    break;
                case "yesterday":
                    dueInstant = Instant.now().minus(1, ChronoUnit.DAYS);
                    break;
                case "tomorrow":
                    dueInstant = Instant.now().plus(1, ChronoUnit.DAYS);
                    break;
                default:
                    dueInstant = Instant.parse(dueDate); 
            }

            Task task = new Task(title, "short", "long", dueInstant, testUser,
                    TaskPriority.MEDIUM, TaskStatus.valueOf(status));
            tasks.add(task);
        }
    }

    @When("the system performs the scheduled task check")
    public void the_system_performs_the_scheduled_task_check() {
        if (notificationsDisabled) {
            reminderTasks.clear();
            return;
        }

        reminderTasks = tasks.stream()
                .filter(t -> t.getStatus() != TaskStatus.CLOSED)
                .filter(t -> !t.getDue_date().isAfter(Instant.now())) 
                .collect(Collectors.toList());
    }

    @Then("the user {string} should receive reminders for the following tasks:")
    public void the_user_should_receive_reminders_for_the_following_tasks(String email, DataTable dataTable) {
        List<String> expectedTitles = dataTable.asMaps().stream()
                .map(row -> row.get("title"))
                .collect(Collectors.toList());

        List<String> actualTitles = reminderTasks.stream()
                .map(Task::getTitle)
                .collect(Collectors.toList());

        for (String title : expectedTitles) {
            assertTrue(actualTitles.contains(title), "Expected reminder for task: " + title);
        }
    }

    @Then("the user {string} should not receive a reminder for:")
    public void the_user_should_not_receive_a_reminder_for(String email, DataTable dataTable) {
        List<String> notExpectedTitles = dataTable.asMaps().stream()
                .map(row -> row.get("title"))
                .collect(Collectors.toList());

        List<String> actualTitles = reminderTasks.stream()
                .map(Task::getTitle)
                .collect(Collectors.toList());

        for (String title : notExpectedTitles) {
            assertFalse(actualTitles.contains(title), "Did not expect reminder for task: " + title);
        }
    }

    @Given("the user {string} has disabled notifications")
    public void the_user_has_disabled_notifications(String email) {
        notificationsDisabled = true;
    }

    @Then("no reminders should be sent to the user")
    public void no_reminders_should_be_sent_to_the_user() {
        assertTrue(reminderTasks.isEmpty(), "Expected no reminders to be sent.");
    }
}
