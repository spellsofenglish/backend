package ru.spellsofenglish.gameservice.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
    private final String PATH="resources/";

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CacheManager cacheManager) {
        this.taskRepository = taskRepository;
        this.cacheManager = cacheManager;
    }

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

    @PostConstruct
    public void loadAllDataToCache() {
        Cache cache = cacheManager.getCache("tasks");
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            cache.put(task.getId(), task);
        }
    }

}
