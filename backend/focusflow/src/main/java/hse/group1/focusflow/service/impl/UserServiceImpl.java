package hse.group1.focusflow.service.impl;

import hse.group1.focusflow.model.Team;
import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.repository.TeamRepository;
import hse.group1.focusflow.repository.UserRepository;
import hse.group1.focusflow.service.UserService;
import org.springframework.stereotype.Service;

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
    // Optional: Check if user already exists
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Email already in use");
    }

    // Map DTO to Entity
    User user = new User();
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword()); // get hashed in User#setPassword()
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());

    // Optional: assign to team
    if (dto.getTeamId() != null) {
      Team team = teamRepository
        .findById(dto.getTeamId())
        .orElseThrow(() -> new IllegalArgumentException("Team not found"));
      user.setTeam(team);
    }

    userRepository.save(user);
  }
}
