package hse.group1.focusflow.model.dto;

public class UserRegistrationDto {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Long teamId;

  // Constructors
  public UserRegistrationDto() {}

  public UserRegistrationDto(
    String email,
    String password,
    String firstName,
    String lastName,
    Long teamId
  ) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.teamId = teamId;
  }

  // Getters and Setters
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public Long getTeamId() {
    return teamId;
  }

  public void setTeamId(Long teamId) {
    this.teamId = teamId;
  }
}
