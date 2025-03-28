package hse.group1.focusflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Team {

  // Primary key
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long team_id;

  // Basic fields
  private String name;
  private String description;

  // Constructor
  public Team() {}

  // Getters and Setters
  public Long getId() {
    return team_id;
  }

  public void setId(Long team_id) {
    this.team_id = team_id;
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
