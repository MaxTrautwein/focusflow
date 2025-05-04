package hse.group1.focusflow.steps;
import io.cucumber.java.en.*;

public class CreateTaskSteps {
    @Given("the user is logged into FocusFlow")
    public void the_user_is_logged_into_focus_flow() {
        // TODO document why this method is empty


    }

    @Given("the user is on the task management interface")
    public void the_user_is_on_the_task_management_interface() {
        // TODO document why this method is empty

    }

    @When("the user clicks on the \"Create New Task\" button")
    public void the_user_clicks_on_the_create_new_task_button() {
        // TODO document why this method is empty
    }

    @When("fills in the title {string}")
    public void fills_in_the_title(String string) {
        // TODO document why this method is empty
        System.out.println("Titel ausgefüllt: " + string);
    }

    @When("fills in the description {string}")
    public void fills_in_the_description(String string) {
        // TODO document why this method is empty
    }

    @When("sets the due date to a future date")
    public void sets_the_due_date_to_a_future_date() {
        // TODO document why this method is empty
    }

    @When("selects priority {string}")
    public void selects_priority(String string) {
        // TODO document why this method is empty
    }

    @When("submits the form")
    public void submits_the_form() {
        // TODO document why this method is empty
    }

    @Then("the task should be saved in the system")
    public void the_task_should_be_saved_in_the_system() {
        // TODO document why this method is empty
    }

    @Then("it should appear in the user's task list with status {string}")
    public void it_should_appear_in_the_users_task_list_with_status(String string) {
        // TODO document why this method is empty
    }

    @When("leaves the title empty")
    public void leaves_the_title_empty() {
        // TODO document why this method is empty
    }

    @Then("the system should display an error message saying {string}")
    public void the_system_should_display_an_error_message_saying(String string) {
        // TODO document why this method is empty
    }

    @When("sets the due date to a past date")
    public void sets_the_due_date_to_a_past_date() {
        // TODO document why this method is empty
    }

    @Given("the database is temporarily unavailable")
    public void the_database_is_temporarily_unavailable() {
        // TODO document why this method is empty
    }

    @When("the user tries to submit a valid task")
    public void the_user_tries_to_submit_a_valid_task() {
        // TODO document why this method is empty
    }

    @Then("the system should display a warning message saying {string}")
    public void the_system_should_display_a_warning_message_saying(String string) {
        // TODO document why this method is empty
    }
}
