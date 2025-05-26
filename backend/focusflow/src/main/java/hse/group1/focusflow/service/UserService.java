package hse.group1.focusflow.service;

import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserDto;
import hse.group1.focusflow.model.dto.UserLoginDto;
import hse.group1.focusflow.model.dto.UserRegistrationDto;
import hse.group1.focusflow.model.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  Boolean register(UserRegistrationDto dto);
  User login(UserLoginDto dto);
  UserDto getProfile(String email);
  UserDto updateProfile(String email, UserUpdateDto update);
}
