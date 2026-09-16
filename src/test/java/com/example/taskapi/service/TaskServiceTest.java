package com.example.taskapi.service;

import com.example.taskapi.dto.TaskRequest;
import com.example.taskapi.exception.TaskNotFoundException;
import com.example.taskapi.model.Task;
import com.example.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = org.mockito.Mockito.mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void shouldReturnAllTasks() {

        Task task1 = new Task(
                1L,
                "Study Java",
                "Learn Spring Boot",
                false
        );

        Task task2 = new Task(
                2L,
                "Study Docker",
                "Create application container",
                false
        );

        when(taskRepository.findAll())
                .thenReturn(List.of(task1, task2));

        var tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        assertEquals("Study Java", tasks.get(0).getTitle());
    }

    @Test
    void shouldFindTaskById() {

        Task task = new Task(
                1L,
                "Study Java",
                "Learn Spring Boot",
                false
        );

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Study Java", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenFindingNonExistingTask() {

        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(999L)
        );

        assertEquals(
                "Task not found: 999",
                exception.getMessage()
        );
    }

    @Test
    void shouldCreateTask() {

        TaskRequest request = new TaskRequest();
        request.setTitle("Studying Kubernetes");
        request.setDescription("Learning Pods and Deployments");

        Task savedTask = new Task(
                3L,
                "Studying Kubernetes",
                "Learning Pods and Deployments",
                false
        );

        when(taskRepository.save(org.mockito.ArgumentMatchers.any(Task.class)))
                .thenReturn(savedTask);

        Task task = taskService.createTask(request);

        assertEquals(3L, task.getId());
        assertEquals(
                "Studying Kubernetes",
                task.getTitle()
        );
        assertEquals(
                "Learning Pods and Deployments",
                task.getDescription()
        );
        assertFalse(task.isCompleted());
    }

    @Test
    void shouldUpdateTask() {

        Task existingTask = new Task(
                1L,
                "Study Java",
                "Learn Spring Boot",
                false
        );

        TaskRequest request = new TaskRequest();
        request.setTitle("Java + Spring Boot");
        request.setDescription("Learn API development");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        Task updatedTask = taskService.updateTask(1L, request);

        assertEquals(1L, updatedTask.getId());
        assertEquals(
                "Java + Spring Boot",
                updatedTask.getTitle()
        );
        assertEquals(
                "Learn API development",
                updatedTask.getDescription()
        );
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingTask() {

        TaskRequest request = new TaskRequest();
        request.setTitle("New Task");
        request.setDescription("Description");

        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(999L, request)
        );
    }

    @Test
    void shouldDeleteTask() {

        when(taskRepository.existsById(2L))
                .thenReturn(true);

        taskService.deleteTask(2L);

        org.mockito.Mockito.verify(taskRepository)
                .deleteById(2L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingTask() {

        when(taskRepository.existsById(999L))
                .thenReturn(false);

        TaskNotFoundException exception = assertThrows(
                TaskNotFoundException.class,
                () -> taskService.deleteTask(999L)
        );

        assertEquals(
                "Task not found: 999",
                exception.getMessage()
        );
    }
}


