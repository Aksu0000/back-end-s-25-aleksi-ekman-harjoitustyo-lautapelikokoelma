package backend25.boardgames;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;
import backend25.boardgames.domain.Genre;
import backend25.boardgames.domain.GenreRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardgameRepository boardgameRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Boardgame testGame;
    private Genre testGenre;

    @BeforeEach
    public void setupTestData() {
        // Poista kaikki "TestGenre" genret
        List<Genre> existingGenres = genreRepository.findByName("TestGenre");
        existingGenres.forEach(genreRepository::delete);

        // Poista kaikki "Test Game" pelit
        List<Boardgame> existingGames = boardgameRepository.findByTitleContainingIgnoreCase("Test Game");
        existingGames.forEach(boardgameRepository::delete);

        // Luo testigenre
        testGenre = new Genre("TestGenre");
        genreRepository.save(testGenre);

        // Luo testipeli
        testGame = new Boardgame("Test Game", 2010, 1, 4, testGenre, "https://cf.geekdo-images.com/4KSmlm59w0GwLIlgDnJDAQ__imagepage/img/Zb31zpGVlnDGPeHj75Xkye1KDNE=/fit-in/900x600/filters:no_upscale():strip_icc()/pic8211747.png");
        boardgameRepository.save(testGame);
    }

    @AfterEach
    public void cleanUpTestData() {
        // Poistetaan testidata
        boardgameRepository.findByTitleContainingIgnoreCase("Test Game")
                .forEach(boardgameRepository::delete);
        genreRepository.findByName("TestGenre")
                .forEach(genreRepository::delete);
    }

    @Test
    public void testGetAllBoardgames() throws Exception {
        this.mockMvc.perform(get("/api/boardgames"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Game")));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testAdminCanDeleteBoardgame() throws Exception {
        this.mockMvc.perform(delete("/api/boardgames/{id}", testGame.getId())
                .with(csrf())) // lisää CSRF-token
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = { "USER" })
    public void testUserCannotDeleteBoardgame() throws Exception {
        this.mockMvc.perform(delete("/api/boardgames/{id}", testGame.getId())
                .with(csrf())) // CSRF myös USER-testissä
                .andExpect(status().isForbidden());
    }
}