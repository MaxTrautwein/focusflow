package hse.group1.focusflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Entity
@Table(name = "\"user\"") // user is reserved in postgres -> must quote name
public class User {

  // Basic fields
  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long userId;

  @NotNull(message = "Email cannot be null")
  @Email(message = "Email should be valid")
  private String email;

  @NotNull(message = "Password cannot be null")
  private String password;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  // Timestamps
  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "last_login")
  private Instant lastLogin;

  // Relationships
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  @OneToMany(mappedBy = "user")
  private List<Task> tasks;

  // Constructors
  public User() {}

  /**
   * @param email E-Mail of the User
   * @param password Clear Text PW (Will be hashed & Salted)
   * @param first_name First Name of User
   * @param last_name Last Name of user
   */
  public User(
    String email,
    String password,
    String firstName,
    String lastName
  ) {
    this.email = email;
    this.setPassword(password);
    this.firstName = firstName;
    this.lastName = lastName;
    this.createdAt = Instant.now();
  }

  // Getters and Setters

  // Only allow Get on id
  public Long getId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public void setPassword(String password) {
    Argon2PasswordEncoder encoder =
      Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    this.password = encoder.encode(password);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getLastLogin() {
    return lastLogin;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  // Helper methods
  /**
   * Updates the last_login with the current Time
   */
  public void updateLastLogin() {
    this.lastLogin = Instant.now();
  }

  /** Checks if a Given PW is correct
   * @param password Plain text PW
   * @return True if the Password is correct
   */
  public boolean passwordMatches(String password) {
    Argon2PasswordEncoder encoder =
      Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    return encoder.matches(password, this.password);
  }
}
