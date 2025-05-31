package hse.group1.focusflow.controller;

import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserDto;
import hse.group1.focusflow.model.dto.UserLoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.model.dto.UserUpdateDto;
import hse.group1.focusflow.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<?> registerUser(
    @RequestBody @Valid UserRegistrationDto dto
  ) {
    try {
      userService.register(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(
        Map.of("message", "User registered successfully.")
      );
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode())
                           .body(Map.of("message", e.getReason()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto dto) {
    try {
      User user = userService.login(dto);
      return ResponseEntity.ok(user);
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode())
                           .body(Map.of("message", e.getReason()));
    }
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> getProfile(@RequestParam String email) {
    UserDto dto = userService.getProfile(email);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/me")
  public ResponseEntity<UserDto> updateProfile(
    Principal principal,
    @RequestBody @Valid UserUpdateDto update
  ) {
    return ResponseEntity.ok(
      userService.updateProfile(principal.getName(), update)
    );
  }
}
