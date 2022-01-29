package io.github.xpakx.wordle.service;

import io.github.xpakx.wordle.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WordScheduledService {
    private final WordRepository wordRepository;

    @Scheduled(cron = "@midnight")
    public void scheduleWordChoice() {
        
    }
}
