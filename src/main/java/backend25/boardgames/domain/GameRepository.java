package backend25.boardgames.domain;

import backend25.boardgames.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByTitle(String title);
}