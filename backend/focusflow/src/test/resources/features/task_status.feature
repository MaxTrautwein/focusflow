Feature: View and update task status

  As a FocusFlow user
  I want to view and check the status of tasks
  So that I can keep track of my progress and take action if tasks are overdue

  Background:
    Given the following tasks exist for user "bob@example.com":
      | title               | status       | dueDate     |
      | Write documentation | OPEN         | today       |
      | Deploy to staging   | PENDING      | yesterday   |
      | Archive old tasks   | CLOSED       | last week   |

  Scenario: User updates a task's status to closed
    When the user "bob@example.com" updates the task "Write documentation" to "CLOSED"
    Then the task "Write documentation" should have status "CLOSED"

  Scenario: System checks for overdue task
    When the system performs the overdue status check
    Then the task "Deploy to staging" should have status "PENDING"
    And the task "Deploy to staging" should be considered overdue

  Scenario: User attempts to update task with invalid status
    When the user "bob@example.com" updates the task "Archive old tasks" to "FINISHED"
    Then the system should show an error: "Invalid task status: FINISHED"

  Scenario: Task does not exist anymore
    When the user "bob@example.com" updates the task "Nonexistent task" to "OPEN"
    Then the system should show a warning: "Task not found"

  Scenario: Database failure during status update
    Given the database is temporarily not available
    When the user "bob@example.com" updates the task "Write documentation" to "IN_REVIEW"
    Then the system should show a retry option to the user
