package hse.group1.focusflow.model.mapper;

import org.springframework.stereotype.Component;

import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.UserDto;

@Component
public class UserMapper {

    /**
     * Convert a User entity into a UserDto.
     */
    public UserDto toDto(User user) {
        Long teamId = (user.getTeam() != null)
                ? user.getTeam().getId()
                : null;

        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                teamId);
    }

    /**
     * Populate an existing User entity with values from a UserDto.
     * 
     */
    public void updateEntityFromDto(UserDto dto, User user) {
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
    }
}
