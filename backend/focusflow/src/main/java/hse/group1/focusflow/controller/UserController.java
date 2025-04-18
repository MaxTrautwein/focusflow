package hse.group1.focusflow.controller;

import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
