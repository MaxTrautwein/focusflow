Feature: View and update task status

  As a FocusFlow user
  I want to view and change the status of tasks
  So that I can keep track of my progress and stay organized

  Background:
    Given the following tasks exist for user "bob@example.com":
      | title               | status       | dueDate     |
      | Write documentation | OPEN         | today       |
      | Deploy to staging   | IN_PROGRESS  | yesterday   |
      | Archive old tasks   | DONE         | last week   |

  Scenario: User updates a task's status to Done
    When the user "bob@example.com" updates the task "Write documentation" to "DONE"
    Then the task "Write documentation" should have status "DONE"

  Scenario: System marks a task as overdue
    When the system performs the overdue status check
    Then the task "Deploy to staging" should be marked as "OVERDUE"

  Scenario: User attempts to update task with invalid status
    When the user "bob@example.com" updates the task "Archive old tasks" to "FINISHED"
    Then the system should show an error: "Invalid task status: FINISHED"

  Scenario: Task does not exist anymore
    When the user "bob@example.com" updates the task "Nonexistent task" to "OPEN"
    Then the system should show a warning: "Task not found"

  Scenario: Database failure during status update
    Given the database is temporarily unavailable
    When the user "bob@example.com" updates the task "Write documentation" to "IN_PROGRESS"
    Then the system should show a retry option to the user
