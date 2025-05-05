Feature: Remind user for tasks due soon or overdue

  As a registered user of FocusFlow
  I want to receive reminders for tasks that are due or overdue
  So that I can take timely action on them

  Background:
    Given the user "alice@example.com" is assigned to the following tasks:
      | title                     | dueDate     | status    |
      | Finalize project report   | today       | OPEN      |
      | Submit expense receipts   | yesterday   | IN_REVIEW |
      

  Scenario: Remind user of due and overdue tasks
    When the system performs the scheduled task check
    Then the user "alice@example.com" should receive reminders for the following tasks:
      | title                   | dueDate   |
      | Finalize project report | today     |
      | Submit expense receipts | yesterday |

  Scenario: No reminders for done tasks or future tasks
    When the system performs the scheduled task check
    Then the user "alice@example.com" should not receive a reminder for:
      | title                   |
      | Clean up old branches  |
      | Prepare sprint demo    |

  Scenario: User has disabled reminders
    Given the user "alice@example.com" has disabled notifications
    When the system performs the scheduled task check
    Then no reminders should be sent to the user
