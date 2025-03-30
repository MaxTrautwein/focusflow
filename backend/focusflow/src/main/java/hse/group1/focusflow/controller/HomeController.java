package hse.group1.focusflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  /**
   * Returns a simple message to confirm successful login and routing.
   * @return A welcome message.
   */
  @GetMapping("/")
  public String welcome() {
    return "Login successful! Welcome to FocusFlow. <br><br> It's wednesday my dudes!";
  }
}
