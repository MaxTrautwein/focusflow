package hse.group1.focusflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@Entity
public class Task {

  // Primary key
  @Id
  @GeneratedValue
  private Long task_id;

  // Basic fields
  @NotNull(message = "title must be set")
  @Size(min = 1, message = "title must be set")
  private String title;
  private String short_description;
  private String long_description;
  @Future(message = "due_date must be in the future") // I'm not sue if that the best way to do that
  private Instant due_date;

  // Relationships
  @ManyToOne
  @JoinColumn(name = "user_id") // foreign key to User table
  private User user;

  // Enums
  @Enumerated(EnumType.STRING)
  private TaskPriority priority;

  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  // Constructor
  public Task() {}

  /**
   *
   * @param title Title of the Task
   * @param short_description a Short Description
   * @param long_description a Long Description
   * @param due_date a due date
   * @param user the Assignee or NULL
   * @param priority the Priority
   * @param status the Status
   */
  public Task(
    String title,
    String short_description,
    String long_description,
    Instant due_date,
    User user,
    TaskPriority priority,
    TaskStatus status
  ) {
    this.title = title;
    this.short_description = short_description;
    this.long_description = long_description;
    this.due_date = due_date;
    this.user = user;
    this.priority = priority;
    this.status = status;
  }

  // Getters and Setters

  // Only allow Get on id
  public Long getId() {
    return task_id;
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

  public void setShort_description(String short_description) {
    this.short_description = short_description;
  }

  public String getLong_description() {
    return long_description;
  }

  public void setLong_description(String long_description) {
    this.long_description = long_description;
  }

  public Instant getDue_date() {
    return due_date;
  }

  public void setDue_date(Instant due_date) {
    this.due_date = due_date;
  }

  public User getAssignee(){
    return user;
  }

  public TaskStatus getStatus(){
    return status;
  }

  public void setStatus(TaskStatus status){
    this.status = status;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public void setPriority(TaskPriority priority) {
    this.priority = priority;
  }



  // Helper methods
  /**
   * Checks, if due date is in the past.
   * @return true, if due date is in the past and the Task is still Pending, false otherwise.
   */
  public boolean isOverdue() {
    return IsOverdueIn(0, ChronoUnit.DAYS);
  }

  /**
   * Checks if this Ticket Will be Due in
   * @param amount Amount
   * @param temporalUnit Minutes, Days, ...
   * @return True if Overdue by then
   */
  public boolean IsOverdueIn(long amount, TemporalUnit temporalUnit){
    // Items without Due Date or Closed ones can't be due
    if (due_date == null || status == TaskStatus.CLOSED){
      return false;
    }

    return due_date.isBefore(Instant.now().plus(amount,temporalUnit));
  }

  /**
   * Checks if the task is assigned to a user.
   * @return true, if a user is assigned, false otherwise.
   */
  public boolean isAssigned() {
    return this.user != null;
  }

  /**
   * Assigns the task to a user.
   * @param user the user to assign the task to.
   */
  public void assignTo(User user) {
    this.user = user;
  }

}
