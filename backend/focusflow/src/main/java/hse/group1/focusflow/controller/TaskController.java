package hse.group1.focusflow.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hse.group1.focusflow.model.dto.TaskDto;
import hse.group1.focusflow.service.TaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@Valid @RequestBody TaskDto dto) {
        TaskDto created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/tasks/" + created.getTask_id()))
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try{
            TaskDto taskDto = service.get(id);
            return ResponseEntity.ok(taskDto);
        }catch (IllegalArgumentException exception) {
            return ResponseEntity.status(404).body(exception.getMessage());
        }
    }

    @GetMapping
    public List<TaskDto> list() {
        return service.listAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TaskDto dto) {
        try{
        TaskDto updatedTask = service.update(id, dto);
        return ResponseEntity.ok(updatedTask);
        }catch(IllegalArgumentException exception) {
            return ResponseEntity.status(404).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
