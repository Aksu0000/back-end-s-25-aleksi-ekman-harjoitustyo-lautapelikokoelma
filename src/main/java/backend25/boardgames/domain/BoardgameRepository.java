package backend25.boardgames.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BoardgameRepository extends CrudRepository<Boardgame, Long> {
    List<Boardgame> findByTitle(String title);
    
}
