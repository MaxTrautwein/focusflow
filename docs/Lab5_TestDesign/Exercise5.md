# Exercise 5 - Test Design

## Exercise 5.1 (20 Points): Black-Box Testing Techniques

### Equivalence Class Partitioning:

| Equivalence Class                                                |  Result |                             Explenation                             | Example Password |
| :--------------------------------------------------------------- | ------: | :-----------------------------------------------------------------: | :--------------: |
| No Characters                                                    | Invalid |             Completely empty input violates all rules.              |                  |
| >10 Characters                                                   | Invalid |                Too short to meet length requirement.                |      Ab1!A       |
| <12 Characters                                                   | Invalid |                    Exceeds maximum length limit.                    |  Abcdef1!23456Q  |
| 0 uppercase letter                                               | Invalid |          Meets other criteria but lacks uppercase letters.          |    abcdef!123    |
| 0 lowercase letter                                               | Invalid |            No lowercase characters — rule not fulfilled.            |    ABCDEF!123    |
| 0 special character                                              | Invalid |                   No special characters present.                    |    Abcdef1234    |
| 10<=PW<=12 Characters, 0<uppercase, 0<lowercase, 0<special char. |   Valid | Meets all requirements: length, uppercase, lowercase, special char. |    Abc!def123    |

### Boundary Value Analysis:

| Boudary Value (Password length) |  Result |                          Explenation                          | Example Password |
| :------------------------------ | ------: | :-----------------------------------------------------------: | :--------------: |
| 9 Characters                    | Invalid |            Too short (1 char below valid minimum).            |    Abc!1234a     |
| 10 Characters                   | depends | Exactly 10 characters, valid if it meets all character rules. |    A1bcdef!gh    |
| 12 Characters                   | depends |           Maximum valid length and meets all rules.           |   Ab!1cdefgh12   |
| 13 Characters                   | Invalid |                    Exceeds max length by 1                    |  Ab!1cdefgh123   |

### Decision Table:

| Attribute                            | No PW  | Too Long | Correct PW Lower | Correct PW Upper | No Upper | No Lower | No Special | Other Special |
| ------------------------------------ | ------ | -------- | ---------------- | ---------------- | -------- | -------- | ---------- | ------------- |
| Has Upper Case                       | no     | yes      | yes              | yes              | no       | yes      | yes        | yes           |
| Has Lower Case                       | no     | yes      | yes              | yes              | yes      | no       | yes        | yes           |
| Has Special Character (!@#$%^&\*)    | no     | yes      | yes              | yes              | yes      | yes      | no         | no            |
| Has Other Special Char (<>,.:;³² µ-) | no     | no       | no               | no               | no       | no       | no         | yes           |
| Length                               | 0      | 15       | 10               | 12               | 11       | 11       | 11         | 11            |
| Result                               | Reject | Reject   | Accept           | Accept           | Reject   | Reject   | Reject     | Accept        |

### Rational:

#### No PW:

An Empty Password is too Short and has no of the Other aspects and should Therefore be Rejected

#### To Long:

An Password that fulfills all usb Requirements but is too long should be Rejected

#### Correct PW Lower:

A Password that Fulfills all Sub Requirements at the Lower Acceptable Length Boundary should be Accepted

#### Correct PW Upper:

A Password that Fulfills all Sub Requirements at the Upper Acceptable Length Boundary should be Accepted

#### No Upper, No Lower, No Special:

Those all have the same Rational. If the Length is Correct but one of the Special Requirements fails The password should be Rejected.
Other Special:
While Some Special Chars where Provided as an Example We Should also check if other Special Characters will also be Accepted. Since those are also Special Characters and the Length is Correct we expect it to be Acceped

#### Non Mentioned Cases:

There are Other Possible cases Where Only One of the special Cases is Correct, I Argue that we can Ignore them as It should already fail with One of them Missung.
Another Option would be to Test all Rejected Cases Cases where some Part is Missing with an already short Password. I would argue again that we can Skip those as they should already be rejected

### Test Cases List:

| ID  | Source                                            | Password        | Expected Result | Reason                                                                                                                          |
| --- | ------------------------------------------------- | --------------- | --------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| 01  | 2: No PW                                          |                 | Reject          | too Short Passwords will not be accepted. no chars at all is choses as nothing could cause additinal issues                     |
| 02  | 2: Too Long                                       | Aaaaaaa!!!!Aqda | Reject          | a otherwise valid pw will be rejected if the Length is too long                                                                 |
| 03  | 2: Correct PW Lower                               | Ab!qwertzu      | Accept          | Minimum Valid Length shall be accepted                                                                                          |
| 04  | 2:Correct PW Upper                                | Ab!qwertzuIo    | Accept          | Maximum Valid Length shall be accepted                                                                                          |
| 05  | 2: No Upper                                       | aaaa!aaaaaa     | Reject          | a Password that fullfils the Length requierment but is missing an Upper case shall be rejected                                  |
| 06  | 2: No Lower                                       | AAAA!AAAAAA     | Reject          | a Password that fullfils the Length requierment but is missing an lower case shall be rejected                                  |
| 07  | 2: No Special                                     | aaaaAaaaaaa     | Reject          | a Password that fullfils the Length requierment but is missing an Special char shall be rejected                                |
| 08  | 2: Other Special                                  | Aa\|--;²aaa     | Accept          | a Password that fulfills the Length requierment and has other kinds of special chars with upper and lower case shall be acceped |
| 09  | 2: Lower Boundary not reached (PW length)         | Abc!1234a       | Reject          | Password on sign too short                                                                                                      |
| 10  | 2: Lower minimum Boundary (PW length)             | A1bcdef!gh      | Accept          | Password exact minimum of 10 signs                                                                                              |
| 11  | 2: Upper maximum Boundary (PW length)             | Ab!1cdefgh12    | Accept          | Password exact maximum of 12 signs                                                                                              |
| 12  | 2: 2: Upper maximum Boundary exceeded (PW length) | Ab!1cdefgh123   | Reject          | Password to one sign too long                                                                                                   |

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
