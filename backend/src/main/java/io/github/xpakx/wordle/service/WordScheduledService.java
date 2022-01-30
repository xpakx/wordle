package io.github.xpakx.wordle.service;

import io.github.xpakx.wordle.entity.Word;
import io.github.xpakx.wordle.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class WordScheduledService {
    private final WordRepository wordRepository;
    private final Random rand = new Random();

    @Scheduled(cron = "@midnight")
    public void scheduleWordChoice() {
        LocalDateTime date = LocalDateTime.now().minusMonths(1);
        List<Word> words = wordRepository.findByLastActiveAfter(date);
        int randomIndex = rand.nextInt(words.size());
        deactivateOld();
        Word newWord = words.get(randomIndex);
        newWord.setActive(true);
        newWord.setLastActive(date);
        wordRepository.save(newWord);
    }

    private void deactivateOld() {
        List<Word> words = wordRepository.findByActive(true);
        for(Word word : words) {
            word.setActive(false);
        }
        wordRepository.saveAll(words);
    }
}
