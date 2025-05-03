Feature: Task Creation
  As a logged-in user
  I want to create a new task
  So that I can manage my to-dos in FocusFlow

  Background:
    Given the user is logged into FocusFlow
    And the user is on the task management interface

  Scenario: Successfully create a task with all valid fields
    When the user clicks on the "Create New Task" button
    And fills in the title "Implement login"
    And fills in the description "Implement OAuth2 login via GitHub"
    And sets the due date to a future date
    And selects priority "High"
    And submits the form
    Then the task should be saved in the system
    And it should appear in the user's task list with status "Open"

  Scenario: Show error when title is left empty
    When the user clicks on the "Create New Task" button
    And leaves the title empty
    And submits the form
    Then the system should display an error message saying "Title is required"

  Scenario: Show validation error when due date is in the past
    When the user clicks on the "Create New Task" button
    And fills in the title "Fix bug"
    And sets the due date to a past date
    And submits the form
    Then the system should display an error message saying "Due date cannot be in the past"

  Scenario: Show retry warning when database is unavailable
    Given the database is temporarily unavailable
    When the user tries to submit a valid task
    Then the system should display a warning message saying "Task could not be saved. Please try again."
