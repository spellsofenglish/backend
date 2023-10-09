package ru.spellsofenglish.gameservice.mapper;

import org.springframework.stereotype.Service;
import ru.spellsofenglish.gameservice.dto.TaskDto;
import ru.spellsofenglish.gameservice.models.Task;

import java.util.function.Function;
@Service
public class TaskMapperDto implements Function<Task, TaskDto> {

    @Override
    public TaskDto apply(Task task) {
        return new TaskDto(
                task.getIndex(),
                task.getWord(),
                task.getAudio(),
                task.getAudio(),
                task.getAudioMeaning(),
                task.getAudioExample(),
                task.getTextMeaning(),
                task.getTextExample(),
                task.getTranscription(),
                task.getTextExampleTranslate(),
                task.getTextMeaningTranslate(),
                task.getWordTranslate()
        );
    }
}
