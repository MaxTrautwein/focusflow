# Use Case: Create Task

### Name:

	Create task

### Summary:

	A user creates a new task in FocusFlow by entering relevant information such as title, description, due date, and priority. The task is then saved and visible in the user's task list.

### Actor:

	User (a registered person using FocusFlow)

### Triggering Event:

	The user clicks on the "Create New Task" button in the application interface.

### Inputs:

	- Title (string, required)
	- Description (string, optional)
	- Due Date (date, optional)
	- Priority Level (enum: Low, Medium, High)

### Pre-Conditions:

	- The user is logged into FocusFlow.
	- The user has access to the task management interface.

### Process Description:

	- The user navigates to the task list or dashboard.
	- The user clicks "Create New Task".
	- A form appears for entering task details.
	- The user fills out the form and submits it.
	- The backend validates the input and stores the task in the database.
	- The task appears in the task list with default status "Open".

### Exceptions:

	- Required fields (like Title) are left empty → show an error message.
	- Database is temporarily unavailable → show a warning and ask to retry.
	- Due date is set in the past → show validation error.

### Outputs and Post-Conditions:

	- A new task object is created and stored in the database.
	- The task appears in the user's task list.
	- The system may notify the user of successful creation.
