# Exercise 7 - BDD & Gherkin Syntax

## Exercise 7.1 (10 Points): User Stories Definition

Use cases from Exercise2/Lab2 were used to create these feature files:

- [Create task](../../backend/focusflow/src/test/resources/features/create_task.feature)
- [Remind for task due](../../backend/focusflow/src/test/resources/features/remind_for_task_due.feature)
- [Task status](../../backend/focusflow/src/test/resources/features/task_status.feature)
- [Update Task](../../backend/focusflow/src/test/resources/features/update_task.feature)
- [User registration](../../backend/focusflow/src/test/resources/features/user_registration.feature) was only implemented to test Cucumber

Cucumber dependencies were added in the [pom.xml](../../backend/focusflow/pom.xml) file

## Exercise 7.2 (10 Points): Implement Steps Definitions

Step definitions were implemented for the following feature files:

- [Create Task steps](../../backend/focusflow/src/test/java/hse/group1/focusflow/steps/CreateTaskSteps.java)
- [Update task steps](../../backend/focusflow/src/test/java/hse/group1/focusflow/steps/UpdateTaskSteps.java)
- [Task status steps](../../backend/focusflow/src/test/java/hse/group1/focusflow/steps/TaskStatusSteps.java)
- [Remind for task due steps](../../backend/focusflow/src/test/java/hse/group1/focusflow/steps/RemindForTaskDueSteps.java)
- [User Registration steps](../../backend/focusflow/src/test/java/hse/group1/focusflow/steps/UserRegistrationSteps.java)

## Exercise 7.3 (10 Points): BDD Test Automation

### Test Phase Decision

The BDD tests were integrated into the unit test stage of the Maven build. This ensures fast feedback during development and allows regression tests for key user stories (e.g., task updates or user registration) to be validated early. Since the BDD tests simulate high-level behavior rather than interacting with external systems or full deployments, it is more efficient to run them alongside unit tests.
