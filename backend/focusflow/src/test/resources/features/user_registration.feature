Feature: User Registration

  Scenario: Register new user
    Given the user is on the registration page
    When the user fills out the registration form
    Then the user should be registered successfully
