package ru.spellsofenglish.gameservice.models;

import lombok.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
@Cacheable("tasks")
public class Task {
    private String id;
    private int index;
    private int group;
    private int page;
    private String word;
    private String image;
    private String audio;
    private String audioMeaning;
    private String audioExample;
    private String textMeaning;
    private String textExample;
    private String transcription;
    private String textExampleTranslate;
    private String textMeaningTranslate;
    private String wordTranslate;
}
