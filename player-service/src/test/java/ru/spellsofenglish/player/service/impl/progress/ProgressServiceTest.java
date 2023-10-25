package ru.spellsofenglish.player.service.impl.progress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.spellsofenglish.player.dto.progress.ProgressDto;
import ru.spellsofenglish.player.entity.Progress;
import ru.spellsofenglish.player.exception.InvalidDataException;
import ru.spellsofenglish.player.repository.ProgressRepository;
import ru.spellsofenglish.player.service.impl.ProgressServiceImpl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProgressServiceTest {
    private ProgressServiceImpl progressService;

    @Mock
    private ProgressRepository progressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        progressService = new ProgressServiceImpl(progressRepository);
    }

    @Test
    void testUpdateProgress_ValidData_UpdatedProgressReturned() {
        ProgressDto progressDto = new ProgressDto(3,10);
        Progress oldProgress = new Progress();
        oldProgress.setTotalPoints(20);
        oldProgress.setGameLevel(2);

        when(progressRepository.save(any(Progress.class))).thenReturn(oldProgress);

        Progress updatedProgress = progressService.updateProgress(progressDto, oldProgress);

        assertEquals(30, updatedProgress.getTotalPoints());
        assertEquals(3, updatedProgress.getGameLevel());
        assertEquals(6.25, updatedProgress.getProgress());
        verify(progressRepository, times(1)).save(oldProgress);
    }

    @Test
    void testUpdateProgress_InvalidData_ThrowsInvalidDataException() {
        ProgressDto progressDto = new ProgressDto(3,-40);

        Progress oldProgress = new Progress();
        oldProgress.setTotalPoints(20);
        oldProgress.setGameLevel(2);

        assertThrows(InvalidDataException.class, () -> progressService.updateProgress(progressDto, oldProgress));
        verify(progressRepository, never()).save(any(Progress.class));
    }

    @Test
    void testCreateProgress_ValidData_NewProgressCreatedAndReturned() {
        Progress expectedProgress = new Progress();
        expectedProgress.setGameLevel(0);
        expectedProgress.setTotalPoints(40);
        expectedProgress.setProgress(0.0);

        when(progressRepository.save(any(Progress.class))).thenReturn(expectedProgress);

        Progress createdProgress = progressService.createProgress();

        assertEquals(expectedProgress, createdProgress);
        verify(progressRepository, times(1)).save(any(Progress.class));
    }
}
