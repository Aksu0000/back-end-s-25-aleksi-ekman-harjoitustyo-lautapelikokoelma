package backend25.boardgames;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;
import backend25.boardgames.domain.Genre;
import backend25.boardgames.domain.GenreRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardgameRepositoryTest {

    @Autowired
    private BoardgameRepository boardgameRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Genre testGenre;
    private Boardgame testGame;

    @BeforeEach
    public void setupTestData() {
        // Poistetaan mahdollinen vanha testidata
        genreRepository.findByName("TestGenre").forEach(genreRepository::delete);
        boardgameRepository.findByTitleContainingIgnoreCase("Test Game")
                .forEach(boardgameRepository::delete);

        testGenre = new Genre("TestGenre");
        genreRepository.save(testGenre);

        testGame = new Boardgame("Test Game", 2010, 1, 4, testGenre, "https://cf.geekdo-images.com/4KSmlm59w0GwLIlgDnJDAQ__imagepage/img/Zb31zpGVlnDGPeHj75Xkye1KDNE=/fit-in/900x600/filters:no_upscale():strip_icc()/pic8211747.png");
        boardgameRepository.save(testGame);
    }

    @AfterEach
    public void cleanUpTestData() {
        boardgameRepository.findByTitleContainingIgnoreCase("Test Game")
                .forEach(boardgameRepository::delete);
        genreRepository.findByName("TestGenre")
                .forEach(genreRepository::delete);
    }

    @Test
    public void testCreateSearchDeleteBoardgame() {
        List<Boardgame> foundGames = boardgameRepository.findByTitleContainingIgnoreCase("Test Game");
        assertThat(foundGames).hasSize(1);
        assertThat(foundGames.get(0).getGenre().getName()).isEqualTo("TestGenre");

        boardgameRepository.delete(testGame);
        foundGames = boardgameRepository.findByTitleContainingIgnoreCase("Test Game");
        assertThat(foundGames).isEmpty();
    }
}