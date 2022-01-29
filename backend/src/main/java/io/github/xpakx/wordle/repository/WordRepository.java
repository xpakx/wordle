package io.github.xpakx.wordle.repository;

import io.github.xpakx.wordle.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
