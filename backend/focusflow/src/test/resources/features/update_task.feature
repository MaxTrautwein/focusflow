Feature: Update existing task

  As a FocusFlow user
  I want to update the details of a task
  So that I can correct or adjust it as needed

  Background:
    Given the following task exists for user "anna@example.com":
      | title               | description     | dueDate   | priority |
      | Create prototype    | First draft     | tomorrow  | MEDIUM   |

  Scenario: User updates task title and due date
    When the user "anna@example.com" updates the task "Create prototype" with:
      | title       | Create final prototype |
      | dueDate     | next week              |
    Then the task "Create final prototype" should be updated with:
      | dueDate     | next week              |
      | priority    | MEDIUM                 |

  Scenario: User clears required field (title)
    When the user "anna@example.com" updates the task "Create prototype" with:
      | title |                           |
    Then the system should show an error: "Title is required"

  Scenario: User changes priority and description
    When the user "anna@example.com" updates the task "Create prototype" with:
      | description | Add login feature    |
      | priority    | HIGH                 |
    Then the task "Create prototype" should be updated with:
      | description | Add login feature    |
      | priority    | HIGH                 |

  Scenario: Task was deleted before update
    Given the task "Create prototype" was deleted
    When the user "anna@example.com" attempts to update the task with:
      | title | Create updated prototype |
    Then the system should show a warning: "Task not found"

  Scenario: Database update fails
    Given the database is temporarily unavailable
    When the user "anna@example.com" updates the task "Create prototype" with:
      | title | Finalize prototype |
    Then the system should inform the user of the failure and allow a retry
