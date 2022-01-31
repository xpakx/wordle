package io.github.xpakx.wordle.service;

import io.github.xpakx.wordle.entity.Word;
import io.github.xpakx.wordle.entity.dto.WordRequest;
import io.github.xpakx.wordle.entity.dto.WordResponse;
import io.github.xpakx.wordle.error.NonExistentWordException;
import io.github.xpakx.wordle.error.WordTooLongException;
import io.github.xpakx.wordle.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class PuzzleService {
    private final WordRepository wordRepository;

    public WordResponse checkWord(WordRequest answer) {
        Word word = wordRepository.findByActive(true).get(0);
        String[] wordSplit = word.getWord().split("");
        String[] answerSplit = answer.getWord().split("");
        if(wordSplit.length != answerSplit.length) {
            throw new WordTooLongException("Your answer have wrong length!");
        }
        if(!wordRepository.existsByWord(answer.getWord())) {
            throw new NonExistentWordException("Not a real word!");
        }
        WordResponse response = new WordResponse();
        response.setPositions(new ArrayList<>());
        for(int i = 0; i<wordSplit.length; i++) {
           if(answerSplit[i].equals(wordSplit[i])) {
               response.getPositions().add(2);
           } else if(contains(wordSplit, answerSplit[i])) {
               response.getPositions().add(1);
           } else {
               response.getPositions().add(0);
           }
        }
        return response;
    }

    private boolean contains(String[] wordSplit, String answerSplit) {
        return Arrays.asList(wordSplit).contains(answerSplit);
    }
}
