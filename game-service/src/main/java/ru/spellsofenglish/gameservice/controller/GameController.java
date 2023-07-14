package ru.spellsofenglish.gameservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.dto.TaskDto;
import ru.spellsofenglish.gameservice.service.GameService;
import ru.spellsofenglish.gameservice.service.PlayerService;
import ru.spellsofenglish.gameservice.service.TaskService;

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
        return taskService.getTask();
    }
    @GetMapping
    public GameDto getGame(){
        return gameService.getGame();
    }

    @GetMapping("/rollDice")
    public Integer rollDice(){
        Integer randomNumber=new Random().nextInt(12)+2;
        playerService.updatePlayer(randomNumber);
        return randomNumber;
    }

}
