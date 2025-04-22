package hse.group1.focusflow.service.impl;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserDto;
import hse.group1.focusflow.model.dto.UserLoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.model.dto.UserUpdateDto;
import hse.group1.focusflow.repository.TeamRepository;
import hse.group1.focusflow.repository.UserRepository;
import hse.group1.focusflow.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final TeamRepository teamRepository;

  public UserServiceImpl(
    UserRepository userRepository,
    TeamRepository teamRepository
  ) {
    this.userRepository = userRepository;
    this.teamRepository = teamRepository;
  }

  @Override
  public void register(UserRegistrationDto dto) {
    // Check if user already exists
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Email already in use"
      );
    }

    // Map DTO to Entity
    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword()); // hashed in User#setPassword()
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());

    // Assign to team
    if (dto.getTeamId() != null) {
      Team team = teamRepository
        .findById(dto.getTeamId())
        .orElseThrow(() ->
          new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team not found")
        );
      user.setTeam(team);
    }

    userRepository.save(user);
  }

  @Override
  public User login(UserLoginDto dto) {
    // Fetch user by email or reject
    User user = userRepository
      .findByEmail(dto.getEmail())
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.UNAUTHORIZED,
          "Invalid credentials"
        )
      );

    // Verify password
    if (!user.passwordMatches(dto.getPassword())) {
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "Invalid credentials"
      );
    }

    // Update last login timestamp
    user.updateLastLogin();
    userRepository.save(user);

    return user;
  }

  @Override
  public UserDto getProfile(String email) {
    User user = userRepository
      .findByEmail(email)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
      );

    return new UserDto(
      user.getUserId(),
      user.getEmail(),
      user.getFirstName(),
      user.getLastName(),
      user.getTeam() != null ? user.getTeam().getId() : null
    );
  }

  @Override
  public UserDto updateProfile(String email, UserUpdateDto update) {
    User user = userRepository
      .findByEmail(email)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
      );

    user.setEmail(update.getEmail());
    if (update.getPassword() != null && !update.getPassword().isBlank()) {
      user.setPassword(update.getPassword());
    }
    user.setFirstName(update.getFirstName());
    user.setLastName(update.getLastName());

    userRepository.save(user);

    return new UserDto(
      user.getUserId(),
      user.getEmail(),
      user.getFirstName(),
      user.getLastName(),
      user.getTeam() != null ? user.getTeam().getId() : null
    );
  }
}
