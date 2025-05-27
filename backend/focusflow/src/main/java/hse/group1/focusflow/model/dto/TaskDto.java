package hse.group1.focusflow.model.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import hse.group1.focusflow.model.TaskPriority;
import hse.group1.focusflow.model.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TaskDto {

    @JsonProperty(value = "task_id", access = JsonProperty.Access.READ_ONLY)
    private Long task_id;

    @NotNull(message = "title must be set")
    @Size(min = 1, message = "title must be set")
    @Size(max = 100, message = "title must be less than 100 characters")
    private String title;

    private String short_description;
    private String long_description;

    @Future(message = "due_date must be in the future")
    private Instant due_date;

    private UserDto userDto;

    @NotNull(message = "Task status must be set")
    private TaskPriority priority;

    @NotNull(message = "Task status must be set")
    private TaskStatus status;

    public TaskDto() {
    }

    public TaskDto(
            Long task_id,
            String title,
            String short_description,
            String long_description,
            Instant due_date,
            UserDto userDto,
            TaskPriority priority,
            TaskStatus status) {
        this.task_id = task_id;
        this.title = title;
        this.short_description = short_description;
        this.long_description = long_description;
        this.due_date = due_date;
        this.userDto = userDto;
        this.priority = priority;
        this.status = status;
    }

    // Getters and Setters
    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String sd) {
        this.short_description = sd;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String ld) {
        this.long_description = ld;
    }

    public Instant getDue_date() {
        return due_date;
    }

    public void setDue_date(Instant due_date) {
        this.due_date = due_date;
    }

    public UserDto getAssignee() {
        return userDto;
    }

    public void setAssignee(UserDto userDto) {
        this.userDto = userDto;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

}
