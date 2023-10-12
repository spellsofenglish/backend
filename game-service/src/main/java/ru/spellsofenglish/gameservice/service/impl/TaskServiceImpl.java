package ru.spellsofenglish.gameservice.service.impl;

import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.AnswerTaskDto;
import ru.spellsofenglish.gameservice.dto.TaskDto;
import ru.spellsofenglish.gameservice.mapper.TaskMapperDto;
import ru.spellsofenglish.gameservice.models.Task;
import ru.spellsofenglish.gameservice.repository.TaskRepository;
import ru.spellsofenglish.gameservice.service.PlayerService;
import ru.spellsofenglish.gameservice.service.TaskService;


import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapperDto taskMapperDto;
    private final PlayerService playerService;
    private final CacheManager cacheManager;
    private final ResourceLoader resourceLoader;


    public TaskServiceImpl(TaskRepository taskRepository, TaskMapperDto taskMapperDto, PlayerService playerService, CacheManager cacheManager, ResourceLoader resourceLoader) {
        this.taskRepository = taskRepository;
        this.taskMapperDto = taskMapperDto;
        this.playerService = playerService;
        this.cacheManager = cacheManager;
        this.resourceLoader = resourceLoader;
    }
    @Override
    public TaskDto getTask(){
        int randomNumber=new Random().nextInt(3600)+1;
        Task task=(Task)cacheManager.getCache("tasks").get(randomNumber).get();
        assert task != null;
        return taskMapperDto.apply(task);
    }
    @Override
    public ResponseEntity<Resource> getFile(String fileName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:files/" + fileName);
            String fileExtension = FilenameUtils.getExtension(fileName);
            MediaType mediaType = MediaType.parseMediaType(getMediaType(fileExtension));
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String getMediaType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "mp3":
                return "audio/mpeg";
            default:
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
    @Override
    public Map<String, Object> getAudioTask() {
        TaskDto task = getTask();
        Map<String, Object> response = new HashMap<>();
        response.put("index",task.index());
        response.put("audio", task.audio());
        List<String> wordOptions = generateWordOptions(task.word());
        response.put("wordOptions", wordOptions);
        return response;
    }
    @Override
    public List<String> generateWordOptions(String correctWord) {
        List<String> wordOptions = new ArrayList<>();
        wordOptions.add(correctWord);
        wordOptions.add(getTask().word());
        wordOptions.add(getTask().word());
        return wordOptions;
    }
   @Override
    public Map<String, Object> getImageTask() {
        TaskDto task = getTask();
        Map<String, Object> response = new HashMap<>();
        response.put("index",task.index());
        response.put("image", task.image());
        response.put("audioMeaning", task.audioMeaning());
        response.put("textMeaning", task.textMeaningTranslate());
        response.put("audioExample", task.audioExample());
        response.put("textExampleTranslate", task.textExampleTranslate());
        return response;
    }

    @Override
    public ResponseEntity<String> checkAnswerForTask(AnswerTaskDto answerTaskDto) {
        var task=(Task) cacheManager.getCache("tasks").get(answerTaskDto.indexTask()).get();
    if(answerTaskDto.word().toLowerCase().equals(task.getWord()) || answerTaskDto.word().toLowerCase().equals(task.getWordTranslate())){
        playerService.updatePlayerProgress(10,answerTaskDto.playerId());
        playerService.allowOrDenyMovePlayer(answerTaskDto.playerId(),true);
        return ResponseEntity.ok("Your answer is correct");
    }
    else {
        playerService.updatePlayerProgress(-5,answerTaskDto.playerId());
        playerService.allowOrDenyMovePlayer(answerTaskDto.playerId(),true);
        return ResponseEntity.ok("Your answer isn't correct");
    }
    }

    @Override
    @PostConstruct
    public void loadAllDataToCache() {
        Cache cache = cacheManager.getCache("tasks");
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            assert cache != null;
            cache.put(task.getIndex(), task);
        }
    }

}
