package ru.spellsofenglish.gameservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.gameservice.dto.AnswerTaskDto;
import ru.spellsofenglish.gameservice.dto.GameDto;
import ru.spellsofenglish.gameservice.models.Task;
import ru.spellsofenglish.gameservice.repository.TaskRepository;
import ru.spellsofenglish.gameservice.service.GameService;
import ru.spellsofenglish.gameservice.service.TaskService;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    private final TaskService taskService;
    private final GameService gameService;

    @Autowired
    public GameController(TaskService taskService, GameService gameService) {
        this.taskService = taskService;
        this.gameService = gameService;
    }

    @GetMapping("/audioTask")
    public ResponseEntity<Map<String, Object>> getAudioTask() {
    return taskService.getAudioTask();
    }

    @GetMapping("/imageTask")
    public ResponseEntity<Map<String, Object>> getImageTask() {
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
    public ResponseEntity<String> checkAnswerForTask(@RequestBody @Valid AnswerTaskDto answerTaskDto){
        return taskService.checkAnswerForTask(answerTaskDto);
    }


}
