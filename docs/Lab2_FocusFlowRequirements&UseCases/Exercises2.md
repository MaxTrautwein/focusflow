# Exercise 2 - FocusFlow Requirements and Use Cases

## Exercise 2.1 (10 Points): Requirements (Functional System Requirements)

Should reflect key functionalities that FocusFlow must offer its users.

### 1. Task Management

- Users must be able to create, edit and delete tasks
- Each task must have the following features:
  - Title
  - Description
  - Due Date
  - Priority Level
- Users should be able to edit existing tasks (to update status or modify details)

### 2. Task Status Management

- Possible status for tasks:
  - "Open"
  - "In Progress"
  - "Done"
  - "Overdue" (The date of the task has passed)
- The system must automatically change a task's status to "Overdue" if the due date has passed and the task is not marked as "Done"

### 3. Task Organization

- Tasks should be grouped into categories or projects to help users structure their workload
- Users should be able to view and manage tasks within these categories

### 4. User Allocation & Collaboration

- Users can assign tasks to themselves or other team members
- Users should have the option to assign tasks to multiple team members if needed
- The system should notify assigned users automatically when they receive a new task
  - The system should notify assigned users via email or in-app notifications

### 5. Time & Deadline Awareness

- Tasks should have due dates that users can set and modify
- The system must provide reminders/alerts for upcoming or overdue tasks
- Reminders should not unnecessary distract the user

### 6. System Architecture

- FocusFlow must have a 3-tier architecture:
  - Database (Stores tasks and user data)
  - API Backend (Manages data access and business logic)
  - Frontend (Provides the user interface)
- The system should be a web-based application, so it can be accessed from multiple devices

### 7. User Interface & Usability

- The UI should be simple, clear and easy to navigate
- The system should allow users to quickly find, create and update tasks without complex menus
- Therefore users should be able to start using the system without training

### 8. System Extensibility & Future Enhancements

- The system should be modular, so that:
  - Future feature expansions are possible
  - Easy updates to UI, database and backend are possible

## Exercise 2.2 (10 Points): Quality Model

Select and Document the 4 most relevant Quality aspects.

Additionally identify and explain what specific measures should be implemented during the
development of FocusFlow to guarantee the quality aspect of "testability".

### Usability

```mermaid
graph TD;
    A[Usability]-->B.1;
    B.1[Learnability]-->C.1;
    C.1[Simple Layout]-->E.1[User can Learn core features in under 1d after Explanation];

    A-->B.2;
    B.2[Operability]-->C.2.1;
    B.2-->C.2.2;
    C.2.1[Only core features]-->E.2.1[Only the Specified Features are Implemented];
    C.2.2[No Distractions]-->E.2.2[The user is only notified when Required];

  A-->B.3;
  B.3[Accessibility]-->C.3.1;
  B.3-->C.3.2;
  C.3.1[Browser Support]-->E.3.1[Must Support: Chrome, Firefox];
  C.3.2[Small Screen Support]-->E.3.2[Must be usable on: Pixel 8, ...];
```

### Functional Suitability

```mermaid
graph TD;
    A[Functional Suitability]-->B.1;
    A-->B.2;
    A-->B.3;
    B.1[Functional Completeness]-->C.1[All Features necessary features are implemented];
    C.1-->E.1[100% feature coverage]

    B.2[Functional Correctness]-->C.2[Task management functions work correctly];
    C.2 -->E.2[90% test pass rate]

  B.3[Functional appropriateness]-->C.3[Users find the system efficient];
  C.3 -->E.3[75% positive user feedback]

```

### Maintainability

```mermaid
graph TD;
    A[Maintainability]-->B.1;
    A-->B.2;
    A-->B.3;

    B.1[Testability]-->C.1[System should support automated testing];
    C.1-->E.1[80%+ test coverage];

    B.2[Modifiability]-->C.2[Task priority changes];
    C.2-->E.2[No more than 10% code modification];

    B.3[Modularity]-->C.3[System components should be independent];
    C.3-->E.3[80% Module independence];
```

### Portability

```mermaid
graph TD;
    A[Portability]-->B.1;
    A-->B.2;
    B.1[Installability]-->C.1[The System should be Deployed with Containers];
    C.1-->E.1[Setup < 15 min, with Docker-Compose];

    B.2[Adaptability]-->C.2[Parts of the System should be Exchangeable];
    C.2-->E.2[Central Configuration for Connectiondata];
```

### Measures for testability

- Enforce Passing Tests for Pull requests with a minimum test code coverage requirement (maybe 80%+)
- Define Guidelines for Required Tests
- Enforce the Guidelines in Pull Requests
- Automated End-to-End Testing (E2E)
- (Error tracking with tools)

## Exercise 2.3 (15 Points): System Context & Use Cases

### 2.3.1 System context Diagram
</br>

![Image of system context diagram](/docs/Pictures/Context_diagram.png)

</br></br></br>

### 2.3.2 Use case Diagram
</br> 

![Image of Use case diagram](/docs/Pictures/Use_case_diagram.png)


