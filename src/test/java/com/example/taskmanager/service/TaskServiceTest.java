package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus("PENDING");
        task.setUser(user);
    }

    @Test
    void getUserTasks_ShouldReturnListOfUserTasks() {
        // given
        List<Task> expectedTasks = Arrays.asList(task);
        when(taskRepository.findByUser(user)).thenReturn(expectedTasks);

        // when
        List<Task> actualTasks = taskService.getUserTasks(user);

        // then
        assertThat(actualTasks).hasSize(1);
        assertThat(actualTasks.get(0).getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findByUser(user);
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenUserOwnsTask() {
        // given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // when
        Task foundTask = taskService.getTaskById(1L, user);

        // then
        assertThat(foundTask.getId()).isEqualTo(1L);
        assertThat(foundTask.getTitle()).isEqualTo("Test Task");
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {
        // given
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> taskService.getTaskById(999L, user))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task not found");
    }

    @Test
    void getTaskById_ShouldThrowException_WhenUserDoesNotOwnTask() {
        // arrange
        User differentUser = new User();
        differentUser.setId(2L);
        differentUser.setEmail("other@test.com");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // act & assert
        assertThatThrownBy(() -> taskService.getTaskById(1L, differentUser))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Access denied");
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        // arrange
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // act
        Task createdTask = taskService.createTask(task);

        // assert
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenUserOwnsTask() {
        // arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        // act
        taskService.deleteTask(1L, user);

        // assert
        verify(taskRepository, times(1)).delete(task);
    }

}
