package ru.spellsofenglish.gameservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.models.Task;
import ru.spellsofenglish.gameservice.repository.TaskRepository;


import java.util.List;
import java.util.Random;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CacheManager cacheManager;

    @Autowired
    public TaskService(TaskRepository taskRepository, CacheManager cacheManager) {
        this.taskRepository = taskRepository;
        this.cacheManager = cacheManager;
    }

    public Task getTask(){
        Random random=new Random();
        int randomNumber= random.nextInt(3600)+1;
        Task task=taskRepository.findByIndex(randomNumber);
        if(task!=null){
            String imagePath= "resources/" + task.getImage();
            String audioPath= "resources/" + task.getAudio();
            String audioMeaningPath= "resources/" + task.getAudioMeaning();
            String audioExamplePath= "resources/" + task.getAudioExample();
        }
        return task;
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
