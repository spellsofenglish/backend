package ru.spellsofenglish.gameservice.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.spellsofenglish.gameservice.dto.TaskDto;


public interface TaskService {
    TaskDto getTask();
    void loadAllDataToCache();
    ResponseEntity<Resource> getFile(String fileName);
}
