package hse.group1.focusflow.controller;

import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.LoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(
    @RequestBody @Valid UserRegistrationDto dto
  ) {
    try {
      userService.register(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(
        "User registered successfully."
      );
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginDto dto) {
    try {
      User user = userService.login(dto);
      return ResponseEntity.ok(user);
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
  }
}
