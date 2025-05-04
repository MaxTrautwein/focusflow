package hse.group1.focusflow.steps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;




public class UpdateTaskSteps {
    
    @Given("the following task exists for user {string}:")
    public void the_following_task_exists_for_user(String string, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @When("the user {string} updates the task {string} with:")
    public void the_user_updates_the_task_with(String user, String task, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @Then("the task {string} should be updated with:")
    public void the_task_should_be_updated_with(String task, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @Then("the system should give an error: {string}")
    public void the_system_should_give_an_error(String string) {
        // TODO document why this method is empty
    }

    @Given("the task {string} was deleted")
    public void the_task_was_deleted(String task) {
        // TODO document why this method is empty
    }

    @When("the user {string} attempts to update the task with:")
    public void the_user_attempts_to_update_the_task_with(String user, DataTable dataTable) {
        // TODO document why this method is empty
    }

    @Then("the system should give a warning: {string}")
    public void the_system_should_give_a_warning(String message) {
        // TODO document why this method is empty
    }

    @Given("the database has temporarily no connection")
    public void the_database_has_temporarily_no_connection() {
        // TODO document why this method is empty
    }

    @Then("the system should inform the user of the failure and allow a retry")
    public void the_system_should_inform_the_user_of_the_failure_and_allow_a_retry() {
        // TODO document why this method is empty
    }
    
}
