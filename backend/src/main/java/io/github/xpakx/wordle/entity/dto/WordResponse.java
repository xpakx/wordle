package io.github.xpakx.wordle.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WordResponse {
    List<Integer> positions;
}
