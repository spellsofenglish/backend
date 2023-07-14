package ru.spellsofenglish.gameservice.service;

import ru.spellsofenglish.gameservice.dto.TaskDto;

public interface TaskService {
    TaskDto getTask();
    void loadAllDataToCache();
}
