package ru.gb.sem05.HOMEWORK.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gb.sem05.HOMEWORK.decorator.UpdateTaskDeco;
import ru.gb.sem05.HOMEWORK.exceptions.TaskNotFoundException;
import ru.gb.sem05.HOMEWORK.model.Task;
import ru.gb.sem05.HOMEWORK.model.TaskStatus;
import ru.gb.sem05.HOMEWORK.services.FileGateWay;
import ru.gb.sem05.HOMEWORK.services.TaskService;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с задачами
 */

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    @Autowired
    private final FileGateWay fileGateWay;
    private final UpdateTaskDeco updateTaskDeco;
    private final TaskService taskService;

    /**
     * Получение списка задач
     * @return список задач
     */
    @GetMapping()
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Получение задачи по id
     * @param id идентификатор задачи
     * @return задача
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        return optionalTask.orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    /**
     * Просмотр задач по статусам
     * @param status искомый статус
     * @return список задач
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    /**
     * Обновить статус задачи
     * @param id id изменяемой задачи
     * @param status новый статус в параметрах запроса
     * @return измененная задача
     */
    @PutMapping("/update/status/{id}")
    public Task updateTask(@PathVariable Long id, @RequestParam TaskStatus status) {
        Task updatedTask = taskService.getTaskById(id).get();
        updatedTask.setStatus(status);
        return taskService.updateTask(id, updatedTask);
    }

    /**
     * Обновить задачу
     * Принимает все поля для обновления + выводит сообщение об обновлении задачи
     * @param id id изменяемой задачи
     * @param updatedTask   новые данные к обновлению
     * @return обновленная задача
     */
    @PutMapping("/update/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return updateTaskDeco.updateTask(id, updatedTask);
    }

    /**
     * Обновить описание задачи
     * @param id id изменяемой задачи
     * @param description новое описание в параметрах запроса
     * @return обновленная задача
     */
    @PutMapping("/update/description/{id}")
    public Task updateTask(@PathVariable Long id, @RequestParam String description) {
        Task updatedTask = taskService.getTaskById(id).get();
        updatedTask.setDescription(description);
        return taskService.updateTask(id, updatedTask);
    }

    /**
     * Удалить задачу по id
     * @param id идентификатор задачи
     */
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    /**
     * Добавить новую задачу и записывает ее в файл с задачами
     * @param task Задача в теле запроса
     * @return Добавленная задача
     */
    @PostMapping("/add")
    public Task addTask(@RequestBody Task task) {
        fileGateWay.writeToFile(task.getName()+".txt", task.toString());
        return taskService.saveTask(task);
    }
}
