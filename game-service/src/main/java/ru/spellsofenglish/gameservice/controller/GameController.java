package ru.spellsofenglish.gameservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.dto.TaskDto;
import ru.spellsofenglish.gameservice.service.GameService;
import ru.spellsofenglish.gameservice.service.PlayerService;
import ru.spellsofenglish.gameservice.service.TaskService;

import java.io.IOException;
import java.util.Random;


@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    private final TaskService taskService;
    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public GameController(TaskService taskService, GameService gameService, PlayerService playerService) {
        this.taskService = taskService;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/task")
    @Cacheable("tasks")
    public TaskDto getTask(){
        TaskDto task=taskService.getTask();
        return task;
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        return taskService.getFile(fileName);
    }
    @GetMapping
    public GameDto getGame(){
        return gameService.getGame();
    }

    @GetMapping("/rollDice")
    public Integer rollDice(){
            Integer randomNumber = new Random().nextInt(12) + 2;
            playerService.updatePlayerGameLevel(randomNumber);
            return randomNumber;
        }


}
