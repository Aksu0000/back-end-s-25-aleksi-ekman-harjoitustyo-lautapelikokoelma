package backend25.boardgames.service;

import backend25.boardgames.domain.Genre;
import backend25.boardgames.domain.GenreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}