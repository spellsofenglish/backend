package ru.spellsofenglish.gameservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.models.Task;
import ru.spellsofenglish.gameservice.repository.TaskRepository;


import java.util.Random;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

}
