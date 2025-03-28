package hse.group1.focusflow.model;

import jakarta.persistence.*;

@Entity
public class Team {

  // Primary key
  @Id
  @GeneratedValue
  private Long team_id;

  // Basic fields
  private String name;
  private String description;

  // Constructor
  public Team() {}

  public Team(String name, String description) {
    this.name = name;
    this.description = description;
  }

  // Getters and Setters

  // Only allow Get on id
  public Long getId() {
    return team_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
