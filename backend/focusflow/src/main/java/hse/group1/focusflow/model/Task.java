package hse.group1.focusflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;

@Entity
public class Task {

  // Primary key
  @Id
  @GeneratedValue
  private Long task_id;

  // Basic fields
  private String title;
  private String short_description;
  private String long_description;
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
   * @return true, if due date is in the past, false otherwise.
   */
  public boolean isOverdue() {
    return due_date != null && due_date.isBefore(Instant.now());
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
