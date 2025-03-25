# Use Case: Task Status

### Name:

	Task Status

### Summary:

	The user views or updates the status of a task in FocusFlow. Task statuses indicate the current progress and help users stay organized. The system may also automatically update the status to "Overdue" if the due date has passed and the task is not marked as "Done".

### Actor:

	User (a registered person using FocusFlow)

### Triggering Event:

	The user opens a task to view or change its status, or the system automatically evaluates overdue tasks.

### Inputs:

	- Task identifier (ID or reference)
	- New status (if updated manually): "Open", "In Progress", "Done"

### Pre-Conditions:

	- The user is logged in.  
	- The task exists and is accessible to the user.

### Process Description:

	- The user navigates to the task list or project view.
	- The user selects a task and chooses a new status from the status menu.
	- The system validates the input and updates the task’s status in the database.

### Exceptions:

	- The selected status is invalid → show error message  
	- The task no longer exists or was deleted → show warning  
	- Database connection fails → show retry option

### Outputs and Post-Conditions:

	- The task status is updated and saved.  
	- The new status is visible in the task list and details view.  

