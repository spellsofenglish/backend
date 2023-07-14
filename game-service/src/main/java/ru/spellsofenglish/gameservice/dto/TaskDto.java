package ru.spellsofenglish.gameservice.dto;

public record TaskDto(
         Integer index,
         String word,
         String image,
         String audio,
         String audioMeaning,
         String audioExample,
         String textMeaning,
         String textExample,
         String transcription,
         String textExampleTranslate,
         String textMeaningTranslate,
         String wordTranslate
) {
}
