package backend25.boardgames.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend25.boardgames.domain.Genre;
import backend25.boardgames.domain.GenreRepository;

@RestController
public class GenreRestController {
    private final GenreRepository genreRepository;

    public GenreRestController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // Hae kaikki genret JSONina
    @GetMapping("/genres")
    public Iterable<Genre> getGenres() {
        return genreRepository.findAll();
    }

    // Lisää uusi genre JSONilla
    @PostMapping("/genres")
    public Genre addGenre(@RequestBody Genre newGenre) {
        return genreRepository.save(newGenre);
    }
}
