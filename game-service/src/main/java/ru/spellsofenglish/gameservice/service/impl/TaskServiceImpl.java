package ru.spellsofenglish.gameservice.service.impl;

import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.TaskDto;
import ru.spellsofenglish.gameservice.models.Task;
import ru.spellsofenglish.gameservice.repository.TaskRepository;
import ru.spellsofenglish.gameservice.service.TaskService;


import java.util.List;
import java.util.Random;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CacheManager cacheManager;
    private final ResourceLoader resourceLoader;
    private final String PATH="/api/v1/games/";

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CacheManager cacheManager, ResourceLoader resourceLoader) {
        this.taskRepository = taskRepository;
        this.cacheManager = cacheManager;
        this.resourceLoader = resourceLoader;
    }
    @Override
    public TaskDto getTask(){
        int randomNumber=new Random().nextInt(3600)+1;
        Task task=taskRepository.findByIndex(randomNumber);
        return new TaskDto(
                task.getIndex(),
                task.getWord(),
                PATH + task.getImage(),
                PATH + task.getAudio(),
                PATH + task.getAudioMeaning(),
                PATH + task.getAudioExample(),
                task.getTextMeaning(),
                task.getTextExample(),
                task.getTranscription(),
                task.getTextExampleTranslate(),
                task.getTextMeaningTranslate(),
                task.getWordTranslate()
        );
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
    @PostConstruct
    public void loadAllDataToCache() {
        Cache cache = cacheManager.getCache("tasks");
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            cache.put(task.getId(), task);
        }
    }

}
