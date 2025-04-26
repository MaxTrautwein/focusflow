# Exercise 6 - Mock Testing 

## Exercise 6.1 (10 Points): Unit Testing of Models

Validate The Existing Tests for Completeness.

### [TaskTest.java](https://github.com/MaxTrautwein/focusflow/blob/main/backend/focusflow/src/test/java/hse/group1/focusflow/TaskTest.java)
Findings:
- Maybe Test the Edge Cases of Due Date in a more exact way

### [TeamTest.java](https://github.com/MaxTrautwein/focusflow/blob/main/backend/focusflow/src/test/java/hse/group1/focusflow/TeamTest.java)
Findings:
- Test Removing non Mebers
- Test Adding & removing NULL


### [UserTest.java](https://github.com/MaxTrautwein/focusflow/blob/main/backend/focusflow/src/test/java/hse/group1/focusflow/UserTest.java)
Findings:
- getLast_login Actually verify Date
- Test Empty PW
- Use more classes of Invalid Emails

## Exercise 6.2 (10 Points): Service Testing

## Exercise 6.3 (10 Points): Repository Testing

## Exercise 6.4 (10 Points): REST API Controller
Implemented endpoints:
- User registration
- User login
- User profile retrieval and update

Also implemented within:
- Error handling and response codes
- Input validation
