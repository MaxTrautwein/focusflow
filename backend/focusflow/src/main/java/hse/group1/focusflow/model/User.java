package hse.group1.focusflow.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Entity
@Table(name = "\"user\"") // user is reserved in postgres -> must quote name
public class User {

  // Basic fields
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_id;

  private String email;
  private String password;
  private String first_name;
  private String last_name;

  // Timestamps
  private Instant created_at;
  private Instant last_login;

  // Relationships
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  @OneToMany(mappedBy = "user")
  private List<Task> tasks;

  /**
   * @param email E-Mail of the User
   * @param password Clear Text PW (Will be hashed & Salted)
   * @param first_name First Name of User
   * @param last_name Last Name of user
   */
  public User(
    String email,
    String password,
    String first_name,
    String last_name
  ) {
    this.email = email;
    this.setPassword(password);
    this.first_name = first_name;
    this.last_name = last_name;
    this.created_at = Instant.now();
  }

  public User() {}

  // Getters and Setters

  // Only allow Get on id
  public Long getId() {
    return user_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public Instant getCreated_at() {
    return created_at;
  }

  public Instant getLast_login() {
    return last_login;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  // Helper methods
  /**
   * Updates the last_login with the current Time
   */
  public void updateLast_login() {
    this.last_login = Instant.now();
  }

  /** Checks if a Given PW is correct
   * @param password Plain text PW
   * @return True if the Password is correct
   */
  public boolean passwordMatches(String password) {
    Argon2PasswordEncoder encoder =
      Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    return encoder.matches(this.password, password);
  }

  /**
   * Save / Set a Password and Hash it with a salt
   * @param password Password to be Hashed
   */
  public void setPassword(String password) {
    Argon2PasswordEncoder encoder =
      Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    this.password = encoder.encode(password);
  }
}
