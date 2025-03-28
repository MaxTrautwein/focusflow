package hse.group1.focusflow.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

  // Getters and Setters
  public Long getId() {
    return task_id;
  }

  public void setId(Long task_id) {
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
}
