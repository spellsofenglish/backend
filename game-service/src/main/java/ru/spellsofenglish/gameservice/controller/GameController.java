package ru.spellsofenglish.gameservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spellsofenglish.gameservice.models.Task;
import ru.spellsofenglish.gameservice.service.TaskService;

import java.util.Random;


@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    private final TaskService taskService;

    @Autowired
    public GameController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/task")
    @Cacheable("tasks")
    public Task getTask(){
    return taskService.getTask();
    }

    @GetMapping("/rollDice")
    public int rollDice(){
        return new Random().nextInt(12)+2;
    }

}
