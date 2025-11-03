package backend25.boardgames.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/api/genres")
    public Iterable<Genre> getGenres() {
        return genreRepository.findAll();
    }

    // Lisää uusi genre JSONilla
    @PostMapping("/api/genres")
    public Genre addGenre(@RequestBody Genre newGenre) {
        return genreRepository.save(newGenre);
    }

    //Poista genre JASONilla
    // Poista lautapeli ID:n perusteella
    @DeleteMapping("/api/genres/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") Long genreId) {
        if (genreRepository.existsById(genreId)) {
            genreRepository.deleteById(genreId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
