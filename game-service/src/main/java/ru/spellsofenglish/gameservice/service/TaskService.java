package ru.spellsofenglish.gameservice.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.spellsofenglish.gameservice.dto.AnswerTaskDto;
import ru.spellsofenglish.gameservice.dto.TaskDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface TaskService {
    TaskDto getTask();
    void loadAllDataToCache();
    ResponseEntity<Resource> getFile(String fileName);
    List<String> generateWordOptions(String correctWord);
    ResponseEntity<Map<String, Object>> getAudioTask();
    ResponseEntity<Map<String, Object>> getImageTask();
    ResponseEntity<String> checkAnswerForTask(AnswerTaskDto answerTaskDto);
}
