# Exercise 5 - Test Design

## Exercise 5.1 (20 Points): Black-Box Testing Techniques

## Exercise 5.2 (20 Points): White-Box Testing Techniques


### List of Test Cases

This section documents the white-box test cases derived from the internal logic of the createTask(...) method in the TaskServiceImpl.java class. The goal is to cover all execution paths, including both valid and invalid conditions with an explanation for each.

Repository: https://github.com/dgrewe-hse/focusflow/tree/dev/backend/src/main/java/de/hse/focusflow/repository  

Services: https://github.com/dgrewe-hse/focusflow/tree/dev/backend/src/main/java/de/hse/focusflow/service 

---

#### Valid Test Cases / Successful Path

- Valid task creation  
  All required fields are provided, all validations pass (title length, due date, etc.), and all referenced entities (users, team, tags) exist. The task is created and saved to the database.

---

#### Invalid Test Cases

##### Missing Required Fields  
These test cases ensure that the method properly rejects incomplete data by validating the presence of all required inputs using validateRequiredFields(...).

- Missing title  
  Title is null or empty → should throw IllegalArgumentException ("Title is required").

- Missing short description  
  Short description is null or empty → should throw IllegalArgumentException ("Short description is required").

- Missing due date  
  Due date is null → should throw IllegalArgumentException ("Due date is required").

- Missing priority  
  Priority is null → should throw IllegalArgumentException ("Priority is required").

- Missing created by ID  
  CreatedBy ID is null → should throw IllegalArgumentException ("Created by user ID is required").

---

##### Invalid Values  
These test cases target additional input constraints enforced in the createTask(...) method.

- Title too short  
  Title length is less than 1 → should throw IllegalArgumentException ("Title must be between 1 and 100 characters").

- Title too long  
  Title length exceeds 100 characters → should throw the same exception as above.

- Short description too long  
  Short description exceeds 200 characters → should throw IllegalArgumentException ("Short description must be at most 200 characters").

- Past due date  
  Due date is before the current time → should throw IllegalArgumentException ("Due date must be in the future").

---

##### Invalid References  
These test cases test how the method handles IDs pointing to non-existent database entities.

- Assignee does not exist  
  The userRepository.findById(assigneeId) call returns empty → should throw IllegalArgumentException ("Assignee not found").

- Team does not exist  
  The teamRepository.findById(teamId) call returns empty → should throw IllegalArgumentException ("Team not found").

- Creator does not exist  
  The userRepository.findById(createdById) call returns empty → should throw IllegalArgumentException ("Created by user not found").

- Tag does not exist  
  At least one tag ID in tagIds does not exist → should throw IllegalArgumentException ("Tag with ID ... not found").

---

##### Logical Constraint Failures  
These test cases target logic constraints not directly tied to field values but to business rules in the method.

- Double assigning a task (assignee and team)  
  Both assigneeId and teamId are set → should throw IllegalArgumentException ("A task cannot be assigned to both a user and a team simultaneously").





## (Optional) Exercise 5.3 (10 Points): Implementation