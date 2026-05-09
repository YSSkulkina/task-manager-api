package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByEmail(email);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getUserTasks(getCurrentUser());
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id, getCurrentUser());
    }

    @PostMapping
    public Task createTask(@RequestBody TaskRequest request) {
        User user = getCurrentUser();
        Task task = new Task(request.getTitle(), request.getDescription(), request.getStatus(), user);
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        Task updatedTask = new Task(request.getTitle(), request.getDescription(), request.getStatus(), null);
        return taskService.updateTask(id, updatedTask, getCurrentUser());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id, getCurrentUser());
    }

}
