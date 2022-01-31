package io.github.xpakx.wordle.service;

import io.github.xpakx.wordle.entity.Word;
import io.github.xpakx.wordle.entity.dto.WordRequest;
import io.github.xpakx.wordle.entity.dto.WordResponse;
import io.github.xpakx.wordle.error.NonExistentWordException;
import io.github.xpakx.wordle.error.BadLengthException;
import io.github.xpakx.wordle.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class PuzzleService {
    private final WordRepository wordRepository;

    public WordResponse checkWord(WordRequest answer) throws BadLengthException, NonExistentWordException {
        Word word = getActiveWord();
        testIfAnswerIsAcceptable(answer, word);
        return checkLettersMatchesAndGenerateResponse(
                splitWordIntoLetters(word.getWord()),
                splitWordIntoLetters(answer.getWord())
        );
    }

    private void testIfAnswerIsAcceptable(WordRequest answer, Word word) {
        testAnswerLength(word.getWord(), answer.getWord());
        testIfAnswerIsInDatabase(answer);
    }

    private void testIfAnswerIsInDatabase(WordRequest answer) {
        if(!wordRepository.existsByWord(answer.getWord())) {
            throw new NonExistentWordException("Not a real word!");
        }
    }

    private void testAnswerLength(String word, String answer) {
        if(word.length() != answer.length()) {
            throw new BadLengthException("Your answer have wrong length!");
        }
    }

    private String[] splitWordIntoLetters(String word) {
        return word.split("");
    }

    private Word getActiveWord() {
        return wordRepository.findByActive(true).get(0);
    }

    private WordResponse checkLettersMatchesAndGenerateResponse(String[] wordSplit, String[] answerSplit) {
        List<Boolean> matches = transformPositionToMatches(wordSplit, answerSplit);
        HashMap<String, Integer> letterFrequency = getLetterFrequencyMap(wordSplit);
        WordResponse response = new WordResponse();
        response.setPositions(new ArrayList<>());
        for(int i = 0; i< wordSplit.length; i++) {
           if(matches.get(i)) {
               response.getPositions().add(2);
           } else if(letterFrequency.containsKey(answerSplit[i]) && letterFrequency.get(answerSplit[i])>0 && contains(wordSplit, answerSplit[i], matches)) {
               response.getPositions().add(1);
               letterFrequency.put(answerSplit[i], letterFrequency.get(answerSplit[i])-1);
           } else {
               response.getPositions().add(0);
           }
        }
        return response;
    }

    private List<Boolean> transformPositionToMatches(String[] wordSplit, String[] answerSplit) {
        return IntStream.range(0, wordSplit.length)
                .mapToObj((i) -> answerSplit[i].equals(wordSplit[i]))
                .collect(Collectors.toList());
    }

    private HashMap<String, Integer> getLetterFrequencyMap(String[] wordSplit) {
        HashMap<String, Integer> letterFrequency = new HashMap<>();
        for(String letter : wordSplit) {
            letterFrequency.put(letter, incrementFrequency(letterFrequency, letter));
        }
        return letterFrequency;
    }

    private int incrementFrequency(HashMap<String, Integer> letterFrequency, String letter) {
        return letterFrequency.containsKey(letter) ? letterFrequency.get(letter) + 1 : 1;
    }

    private boolean contains(String[] wordSplit, String letter, List<Boolean> matches) {
        return IntStream.range(0, wordSplit.length)
                .filter((i) -> !matches.get(i) && letter.equals(wordSplit[i]))
                .findAny()
                .isPresent();
    }
}
