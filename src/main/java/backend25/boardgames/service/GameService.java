package backend25.boardgames.service;

import backend25.boardgames.dto.BoardGameDto;
import backend25.boardgames.domain.Game;
import backend25.boardgames.domain.GameRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    public Game addGameFromApi(BoardGameDto dto) {
        Game game = new Game();
        game.setTitle(dto.getTitle());
        game.setPublicationYear(dto.getPublicationYear());
        game.setMinPlayers(dto.getMinPlayers());
        game.setMaxPlayers(dto.getMaxPlayers());
        game.setImageUrl(dto.getImageUrl());
        game.setDescription(dto.getDescription());
        game.setSource("BGG");
        return gameRepository.save(game);
    }
}