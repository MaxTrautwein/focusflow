package hse.group1.focusflow.steps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;


public class TaskStatusSteps {

    @Given("the following tasks exist for user {string}:")
    public void the_following_tasks_exist_for_user(String string, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @When("the user {string} updates the task {string} to {string}")
    public void the_user_updates_the_task_to(String string, String string2, String string3) {
        // TODO document why this method is empty
    }

    @Then("the task {string} should have status {string}")
    public void the_task_should_have_status(String string, String string2) {
        // TODO document why this method is empty
    }

    @When("the system performs the overdue status check")
    public void the_system_performs_the_overdue_status_check() {
        // TODO document why this method is empty
    }

    @Then("the task {string} should be marked as {string}")
    public void the_task_should_be_marked_as(String string, String string2) {
        // TODO document why this method is empty
    }

    @Then("the system should show an error: {string}")
    public void the_system_should_show_an_error(String string) {
        // TODO document why this method is empty
    }

    @Then("the system should show a warning: {string}")
    public void the_system_should_show_a_warning(String string) {
        // TODO document why this method is empty
    }

    @Given("the database is temporarily not available")
    public void the_database_is_temporarily_not_available() {
        // TODO document why this method is empty
    }

    @Then("the system should show a retry option to the user")
    public void the_system_should_show_a_retry_option_to_the_user() {
        // TODO document why this method is empty
    }
    
}
