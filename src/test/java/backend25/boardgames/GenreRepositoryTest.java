package backend25.boardgames;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import backend25.boardgames.domain.Genre;
import backend25.boardgames.domain.GenreRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    private Genre testGenre;

    @BeforeEach
    public void setupTestData() {
        genreRepository.findByName("TestGenre2").forEach(genreRepository::delete);

        testGenre = new Genre("TestGenre2");
        genreRepository.save(testGenre);
    }

    @AfterEach
    public void cleanUpTestData() {
        genreRepository.findByName("TestGenre2").forEach(genreRepository::delete);
    }

    @Test
    public void testCreateSearchDeleteGenre() {
        List<Genre> foundGenres = genreRepository.findByName("TestGenre2");
        assertThat(foundGenres).hasSize(1);
        assertThat(foundGenres.get(0).getName()).isEqualTo("TestGenre2");

        genreRepository.delete(testGenre);
        foundGenres = genreRepository.findByName("TestGenre2");
        assertThat(foundGenres).isEmpty();
    }
}