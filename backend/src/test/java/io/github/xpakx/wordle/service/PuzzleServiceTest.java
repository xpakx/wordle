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
}