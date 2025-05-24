package hse.group1.focusflow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hse.group1.focusflow.model.Task;
import hse.group1.focusflow.model.dto.TaskDto;
import hse.group1.focusflow.model.mapper.TaskMapper;
import hse.group1.focusflow.repository.TaskRepository;
import hse.group1.focusflow.repository.UserRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final TaskMapper mapper;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo, TaskMapper mapper) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.mapper   = mapper;
    }

    public TaskDto create(TaskDto dto) {
        Task t = mapper.toEntity(dto);
        Task saved = taskRepo.save(t);
        return mapper.toDto(saved);
    }

    public TaskDto update(Long id, TaskDto dto) {
        Task existing = taskRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
        existing.setTitle(dto.getTitle());
        existing.setShort_description(dto.getShort_description());
        existing.setLong_description(dto.getLong_description());
        existing.setDue_date(dto.getDue_date());
        existing.setPriority(dto.getPriority());
        existing.setStatus(dto.getStatus());
        if (dto.getAssignee() != null) {
            existing.assignTo(userRepo.findById(dto.getAssignee().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        }
        Task saved = taskRepo.save(existing);
        return mapper.toDto(saved);
    }

    public TaskDto get(Long id) {
        return mapper.toDto(taskRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id)));
    }

    public List<TaskDto> listAll() {
        return taskRepo.findAll().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    public void delete(Long id) {
        taskRepo.deleteById(id);
    }
}