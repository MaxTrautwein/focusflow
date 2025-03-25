# Use Case: Update Task

### Name:

	Update Task

### Summary:

	The user modifies the details of an existing task, such as the title, description, due date, or priority. This allows for flexibility in managing changing requirements or correcting initial inputs.

### Actor:

	User (a registered person using FocusFlow)

### Triggering Event:

	The user selects an existing task and chooses the "Edit" or "Update" option.

### Inputs:

	- Task identifier (ID or reference)  
	- Updated values for any of the following fields:
	  - Title (string)
	  - Description (string)
	  - Due Date (date)
	  - Priority Level (enum: Low, Medium, High)

### Pre-Conditions:

	- The user is logged into FocusFlow.  
	- The task exists and is accessible by the user.

### Process Description:

	- The user navigates to the task overview or details page.  
	- The user clicks "Edit" on the desired task.  
	- A form with the current task information is displayed.  
	- The user modifies one or more fields and submits the form.  
	- The backend validates the input and saves the updated task in the database.  
	- The task is updated and shown with the new information.

### Exceptions:

	- Required fields (e.g., Title) are removed or invalid → show error  
	- The task was deleted before the update → show warning and refresh list  
	- The database update fails → inform the user and allow retry

### Outputs and Post-Conditions:

	- The task information is updated and stored.  
	- Changes are visible in the user’s task list or project view.  
	- If due date or priority is changed, related reminders may be adjusted.
