package io.github.xpakx.wordle.repository;

import io.github.xpakx.wordle.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByLastActiveAfter(LocalDateTime lastActive);
    List<Word> findByActive(boolean active);
}
