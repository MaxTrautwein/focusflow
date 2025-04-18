package hse.group1.focusflow.service;

import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.LoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;

public interface UserService {
  void register(UserRegistrationDto dto);
  User login(LoginDto dto);
}
