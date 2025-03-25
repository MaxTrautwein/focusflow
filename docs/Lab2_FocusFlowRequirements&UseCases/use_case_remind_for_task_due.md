# Use Case: Remind for Task Due

### Name:

	Remind for Task Due

### Summary:

	The system checks for tasks that are close to or past their due date and sends reminders to the responsible user(s). The reminder helps users stay aware of upcoming deadlines without needing to manually check the task list.

### Actor:

	User (a registered person using FocusFlow)

### Triggering Event:

	The system performs a scheduled check (e.g., every hour or day) for due or overdue tasks.

### Inputs:

	- Due dates and status of existing tasks stored in the system  
	- User preferences for receiving reminders (e.g., email, in-app notification)

### Pre-Conditions:

	- The user has at least one task with a due date.  
	- The user is assigned to the task.  
	- The system is running and has access to the task database.

### Process Description:

	- A scheduled background process runs (e.g., backend task scheduler).  
	- The system queries the database for all tasks with:
	  - due date = today, or
	  - due date < today and status not equal "Done"
	- For each relevant task, the system checks who is assigned.
	- The system sends a notification to the assigned user(s):
	  - via in-app notification and/or email
	- The notification includes task title, due date, and a link to open the task.

### Exceptions:

	- User has disabled notifications → no alert is sent  
	- Email service or notification system fails → log the error and retry later  
	- The task was deleted or marked “Done” during the check → skip notification

### Outputs and Post-Conditions:

	- A notification is generated and delivered to the user.  
	- The user is informed about the upcoming or missed deadline.  
	- The task remains unchanged unless the user interacts with it.
