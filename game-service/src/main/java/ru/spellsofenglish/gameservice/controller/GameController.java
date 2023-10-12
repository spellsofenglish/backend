package ru.spellsofenglish.gameservice.controller;

import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.gameservice.dto.AnswerTaskDto;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.service.GameService;
import ru.spellsofenglish.gameservice.service.TaskService;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    private final TaskService taskService;
    private final GameService gameService;


    public GameController(TaskService taskService, GameService gameService) {
        this.taskService = taskService;
        this.gameService = gameService;
    }

    @GetMapping("/audioTask")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getAudioTask() {
    return taskService.getAudioTask();
    }

    @GetMapping("/imageTask")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getImageTask() {
        return taskService.getImageTask();
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        return taskService.getFile(fileName);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto getGame(@PathVariable UUID id){
        return gameService.getGame(id);
    }

    @GetMapping("/{id}/rollDice")
    @ResponseStatus(HttpStatus.OK)
    public Integer rollDice(@PathVariable UUID id){
        return gameService.rollDice(id);
        }

    @PostMapping("/answer")
    public ResponseEntity<String> checkAnswerForAudioTask(@RequestBody @Valid AnswerTaskDto answerTaskDto){
        return taskService.checkAnswerForTask(answerTaskDto);
    }


}
