package hse.group1.focusflow.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Team {

  // Primary key
  @Id
  @GeneratedValue
  private Long team_id;

  // Basic fields
  private String name;
  private String description;

  // Relationships
  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<User> members = new ArrayList<>();

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

  public List<User> getMembers() {
    return members;
  }

  public void setMembers(List<User> members) {
    this.members = members;
  }

  // Helper methods

  /**
   * Adds a user to the team if they are not already a member.
   * @param user the User to add.
   */
  public void addMember(User user) {
    if (!hasMember(user)) {
      members.add(user);
      user.setTeam(this); // Keep the relation bidirectional
    }
  }

  /**
   * Removes a user from the team if they are a member.
   * @param user the User to remove.
   */
  public void removeMember(User user) {
    if (hasMember(user)) {
      members.remove(user);
      user.setTeam(null); // Remove bidirectional relation
    }
  }

  /**
   * Checks if a user is part of the team.
   * @param user the User to check.
   * @return true if the user is in the team, false otherwise.
   */
  public boolean hasMember(User user) {
    return members.contains(user);
  }
}
