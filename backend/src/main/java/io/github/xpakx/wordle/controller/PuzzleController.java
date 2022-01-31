package io.github.xpakx.wordle.controller;

import io.github.xpakx.wordle.entity.dto.WordRequest;
import io.github.xpakx.wordle.entity.dto.WordResponse;
import io.github.xpakx.wordle.service.PuzzleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PuzzleController {
    private final PuzzleService service;

    @PostMapping
    public ResponseEntity<WordResponse> addProject(@RequestBody WordRequest request) {
        return  new ResponseEntity<>(service.checkWord(request), HttpStatus.OK);
    }
}
