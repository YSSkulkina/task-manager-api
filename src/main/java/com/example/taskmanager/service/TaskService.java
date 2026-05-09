package com.example.taskmanager.service;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Task getTaskById(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        return task;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask, User user) {
        Task existing = getTaskById(id, user);
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(updatedTask.getStatus());
        return taskRepository.save(existing);
    }

    public void deleteTask(Long id, User user) {
        Task task = getTaskById(id, user);
        taskRepository.delete(task);
    }

}
