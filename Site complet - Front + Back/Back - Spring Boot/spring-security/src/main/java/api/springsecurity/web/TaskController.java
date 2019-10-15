package api.springsecurity.web;

import api.springsecurity.dao.TaskRepository;
import api.springsecurity.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> listTask() {
        return taskRepository.findAll();
    }

    @PostMapping("/task")
    public void addTask(@RequestBody Task t) {
        taskRepository.save(t);
    }
}
