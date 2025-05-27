package hse.group1.focusflow.model.mapper;

import org.springframework.stereotype.Component;

import hse.group1.focusflow.model.Task;
import hse.group1.focusflow.model.User;
import hse.group1.focusflow.model.dto.TaskDto;
import hse.group1.focusflow.model.dto.UserDto;
import hse.group1.focusflow.repository.UserRepository;

@Component
public class TaskMapper {
    private final UserRepository userRepo;

    public TaskMapper(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public TaskDto toDto(Task t) {
        TaskDto dto = new TaskDto();
        dto.setTask_id(t.getId());
        dto.setTitle(t.getTitle());
        dto.setShort_description(t.getShort_description());
        dto.setLong_description(t.getLong_description());
        dto.setDue_date(t.getDue_date());
        if (t.isAssigned()) {
            User uEnt = t.getAssignee();
            UserDto u = new UserDto();
            u.setId(uEnt.getUserId());
            u.setEmail(uEnt.getEmail());
            u.setFirstName(uEnt.getFirstName());
            u.setLastName(uEnt.getLastName());
            u.setTeamId(uEnt.getTeam() != null ? uEnt.getTeam().getId() : null);
            dto.setAssignee(u);
        }
        dto.setPriority(t.getPriority());
        dto.setStatus(t.getStatus());
        return dto;
    }

    public Task toEntity(TaskDto dto) {
        Task t = new Task(
                dto.getTitle(),
                dto.getShort_description(),
                dto.getLong_description(),
                dto.getDue_date(),
                null,
                dto.getPriority(),
                dto.getStatus());
        // look up and assign User if present
        if (dto.getAssignee() != null) {
            User u = userRepo.findById(dto.getAssignee().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.getAssignee().getId()));
            t.assignTo(u);
        }
        return t;
    }
}