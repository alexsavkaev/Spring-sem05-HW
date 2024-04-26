package ru.gb.sem05.HOMEWORK.decorator;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gb.sem05.HOMEWORK.model.Task;
import ru.gb.sem05.HOMEWORK.services.TaskService;

/**
 * Декоратор для обновления задачи. Выводит сообщение об успешном обновлении
 */
@Component
@AllArgsConstructor
public class UpdateTaskDeco  {

    @Autowired
    private final TaskService taskService;

    public Task updateTask(Long id, Task newTask) {
        Task updatedTask = taskService.updateTask(id, newTask);
        if(updatedTask != null) {
            System.out.printf("Задача обновлена с ID: %d", id);
        }
        return taskService.updateTask(id, updatedTask);
    }



}
