package io.github.xpakx.wordle.service;

import io.github.xpakx.wordle.entity.Word;
import io.github.xpakx.wordle.entity.dto.WordRequest;
import io.github.xpakx.wordle.entity.dto.WordResponse;
import io.github.xpakx.wordle.error.BadLengthException;
import io.github.xpakx.wordle.error.NonExistentWordException;
import io.github.xpakx.wordle.repository.WordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PuzzleServiceTest {
    @Mock
    WordRepository repository;

    private PuzzleService service;
    private Word tasty;
    private Word arise;

    @BeforeEach
    void setUp() {
        tasty = new Word(1L, "tasty", null, true);
        arise = new Word(2L, "arise", null, true);
    }

    @AfterEach
    void tearDown() {
    }

    private void injectMocks() {
        service = new PuzzleService(repository);
    }

    @Test
    void shouldThrowExceptionForNonexistentWord() {
        given(repository.findByActive(true))
                .willReturn(List.of(arise));
        injectMocks();
        WordRequest tirty = new WordRequest();
        tirty.setWord("tirty");

        assertThrows(NonExistentWordException.class, () -> service.checkWord(tirty));
    }

    @Test
    void shouldThrowExceptionForTooShortWord() {
        given(repository.findByActive(true))
                .willReturn(List.of(arise));
        injectMocks();
        WordRequest mast = new WordRequest();
        mast.setWord("mast");

        assertThrows(BadLengthException.class, () -> service.checkWord(mast));
    }

    @Test
    void shouldRespondToGoodWord() {
        given(repository.findByActive(true))
                .willReturn(List.of(arise));
        given(repository.existsByWord("arise"))
                .willReturn(true);
        injectMocks();
        WordRequest arise = new WordRequest();
        arise.setWord("arise");

        WordResponse response = service.checkWord(arise);

        assertEquals(2, response.getPositions().get(0)); //"a"
        assertEquals(2, response.getPositions().get(1)); //"r"
        assertEquals(2, response.getPositions().get(2)); //"i"
        assertEquals(2, response.getPositions().get(3)); //"s"
        assertEquals(2, response.getPositions().get(4)); //"e"
    }

    @Test
    void shouldRespondToAlmostGoodWord() {
        given(repository.findByActive(true))
                .willReturn(List.of(arise));
        given(repository.existsByWord("raise"))
                .willReturn(true);
        injectMocks();
        WordRequest raise = new WordRequest();
        raise.setWord("raise");

        WordResponse response = service.checkWord(raise);

        assertEquals(1, response.getPositions().get(0)); //"a"
        assertEquals(1, response.getPositions().get(1)); //"r"
        assertEquals(2, response.getPositions().get(2)); //"i"
        assertEquals(2, response.getPositions().get(3)); //"s"
        assertEquals(2, response.getPositions().get(4)); //"e"
    }

    @Test
    void shouldRespondToBadWordWithDuplicatedLetters() {
        given(repository.findByActive(true))
                .willReturn(List.of(arise));
        given(repository.existsByWord("arras"))
                .willReturn(true);
        injectMocks();
        WordRequest arras = new WordRequest();
        arras.setWord("arras");

        WordResponse response = service.checkWord(arras);

        assertEquals(2, response.getPositions().get(0)); //"a"
        assertEquals(2, response.getPositions().get(1)); //"r"
        assertEquals(0, response.getPositions().get(2)); //"i"
        assertEquals(0, response.getPositions().get(3)); //"s"
        assertEquals(1, response.getPositions().get(4)); //"e"
    }

    @Test
    void shouldRespondToGoodWordWhenAnswerHasDuplicatedLetters() {
        given(repository.findByActive(true))
                .willReturn(List.of(tasty));
        given(repository.existsByWord("tasty"))
                .willReturn(true);
        injectMocks();
        WordRequest tasty = new WordRequest();
        tasty.setWord("tasty");

        WordResponse response = service.checkWord(tasty);

        assertEquals(2, response.getPositions().get(0)); //"t"
        assertEquals(2, response.getPositions().get(1)); //"a"
        assertEquals(2, response.getPositions().get(2)); //"s"
        assertEquals(2, response.getPositions().get(3)); //"t"
        assertEquals(2, response.getPositions().get(4)); //"y"
    }

    @Test
    void shouldRespondToAlmostGoodWordWhenAnswerHasDuplicatedLetters() {
        given(repository.findByActive(true))
                .willReturn(List.of(tasty));
        given(repository.existsByWord("taste"))
                .willReturn(true);
        injectMocks();
        WordRequest taste = new WordRequest();
        taste.setWord("taste");

        WordResponse response = service.checkWord(taste);

        assertEquals(2, response.getPositions().get(0)); //"t"
        assertEquals(2, response.getPositions().get(1)); //"a"
        assertEquals(2, response.getPositions().get(2)); //"s"
        assertEquals(2, response.getPositions().get(3)); //"t"
        assertEquals(0, response.getPositions().get(4)); //"y"
    }

    @Test
    void shouldRespondWhenAnswerHasDuplicatedLettersAndBothAreMisplaced() {
        given(repository.findByActive(true))
                .willReturn(List.of(tasty));
        given(repository.existsByWord("start"))
                .willReturn(true);
        injectMocks();
        WordRequest start = new WordRequest();
        start.setWord("start");

        WordResponse response = service.checkWord(start);

        assertEquals(1, response.getPositions().get(0)); //"t"
        assertEquals(1, response.getPositions().get(1)); //"a"
        assertEquals(1, response.getPositions().get(2)); //"s"
        assertEquals(0, response.getPositions().get(3)); //"t"
        assertEquals(1, response.getPositions().get(4)); //"y"
    }

    @Test
    void shouldRespondWhenAnswerHasDuplicatedLettersAndOneIsRedundant() {
        given(repository.findByActive(true))
                .willReturn(List.of(tasty));
        given(repository.existsByWord("totty"))
                .willReturn(true);
        injectMocks();
        WordRequest totty = new WordRequest();
        totty.setWord("totty");

        WordResponse response = service.checkWord(totty);

        assertEquals(2, response.getPositions().get(0)); //"t"
        assertEquals(0, response.getPositions().get(1)); //"a"
        assertEquals(0, response.getPositions().get(2)); //"s"
        assertEquals(2, response.getPositions().get(3)); //"t"
        assertEquals(2, response.getPositions().get(4)); //"y"
    }

    @Test
    void shouldRespondWhenAnswerHasDuplicatedLettersAndOneIsMisplaced() {
        given(repository.findByActive(true))
                .willReturn(List.of(tasty));
        given(repository.existsByWord("tatar"))
                .willReturn(true);
        injectMocks();
        WordRequest tatar = new WordRequest();
        tatar.setWord("tatar");

        WordResponse response = service.checkWord(tatar);

        assertEquals(2, response.getPositions().get(0)); //"t"
        assertEquals(2, response.getPositions().get(1)); //"a"
        assertEquals(1, response.getPositions().get(2)); //"s"
        assertEquals(0, response.getPositions().get(3)); //"t"
        assertEquals(0, response.getPositions().get(4)); //"y"
    }
}