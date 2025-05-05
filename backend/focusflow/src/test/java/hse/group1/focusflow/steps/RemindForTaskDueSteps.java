package hse.group1.focusflow.steps;
import io.cucumber.java.en.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hse.group1.focusflow.model.Task;
import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;
import io.cucumber.datatable.DataTable;

import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import hse.group1.focusflow.model.User;

public class RemindForTaskDueSteps {

    private User TestUser1;
    List<Task> tasks = new ArrayList<>();

    @Given("the user {string} is assigned to the following tasks:")
    public void the_user_is_assigned_to_the_following_tasks(String email, DataTable dataTable) {
        
        TestUser1 = new User("test@1.de","TestUser1","Test","1");

        for (Map<String, String> row : dataTable.asMaps()) {
        String title = row.get("title");
        String shortDescription = "Test Description";
        String longDescription = "Test short Description";
        Instant due = Instant.now().plus(2, ChronoUnit.DAYS );
        TaskPriority priority = TaskPriority.LOW;
        TaskStatus status = TaskStatus.valueOf(row.get("status").toUpperCase());	
        
        tasks.add(new Task(title, shortDescription,longDescription,
              due, TestUser1, priority, status));
        }

        
    }

    @When("the system performs the scheduled task check")
    public void the_system_performs_the_scheduled_task_check() {
        // TODO document why this method is empty
    }

    @Then("the user {string} should receive reminders for the following tasks:")
    public void the_user_should_receive_reminders_for_the_following_tasks(String string, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @Then("the user {string} should not receive a reminder for:")
    public void the_user_should_not_receive_a_reminder_for(String string, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @Given("the user {string} has disabled notifications")
    public void the_user_has_disabled_notifications(String string) {
        // TODO document why this method is empty
    }

    @Then("no reminders should be sent to the user")
    public void no_reminders_should_be_sent_to_the_user() {
        // TODO document why this method is empty
    }
}
