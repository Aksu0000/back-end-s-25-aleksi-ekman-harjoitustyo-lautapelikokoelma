package backend25.boardgames.web;

import org.springframework.http.ResponseEntity;     // Tuodaan ResponseEntity, jotta voidaan palauttaa HTTP-statuskoodit.
import org.springframework.security.access.prepost.PreAuthorize;    // Anotaatio, jolla voidaan rajoittaa pääsyä metodeihin roolien perusteella.
import org.springframework.web.bind.annotation.*;       // Tuodaan kaikki REST-annotaatiot mm. @RestController, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PathVariable, @RequestBody

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;
import backend25.boardgames.domain.Genre;
import backend25.boardgames.domain.GenreRepository;

@RestController     // Merkitsee, että luokka on REST-kontrolleri. Metodien palautukset muutetaan automaattisesti JSONiksi
public class BoardgameRestController {
    private final BoardgameRepository boardgameRepository;
    private final GenreRepository genreRepository;

	public BoardgameRestController(BoardgameRepository boardgameRepository, GenreRepository genreRepository) {
        this.boardgameRepository = boardgameRepository;
        this.genreRepository = genreRepository;
    }
    //hae kaikki lautapelit
    @GetMapping("/api/boardgames")
    public Iterable<Boardgame> getBoardgames() {
        return boardgameRepository.findAll();   // Palauttaa kaikki Boardgame-oliot JSON-muodossa
    }

    //hae lautapeli id:n perusteella
    @GetMapping("/api/boardgames/{id}")
    public Boardgame getBoardgameById(@PathVariable("id") Long boardgameId) {
        return boardgameRepository.findById(boardgameId).orElse(null);  // Hakee pelin tietokannasta ID:n perusteella. Jos ei löydy, palauttaa null.
    }

    // Lisää uusi lautapeli JSON:lla
    @PostMapping("/api/boardgames")
public Boardgame addBoardgame(@RequestBody Boardgame newBoardgame) {
    Genre genre = genreRepository.findById(newBoardgame.getGenre().getId())
        .orElseThrow(() -> new RuntimeException("Genre not found"));    // Haetaan genre tietokannasta, jos genreä ei löydy, heitetään poikkeus.
    newBoardgame.setGenre(genre);       // Liitetään genre uudelle pelille.
    return boardgameRepository.save(newBoardgame);      // Tallennetaan uusi peli tietokantaan ja palautetaan tallennettu olio JSONina.
}

    
    // päivitä olemassa oleva lautapeli JSON:illa
    @PutMapping("/api/boardgames/{id}")
    public ResponseEntity<Boardgame> editBoardgame(@RequestBody Boardgame editedBoardgame, @PathVariable Long id) {
        return boardgameRepository.findById(id)
                .map(boardgame -> {
                    editedBoardgame.setId(id);  // Varmistetaan, että tallennettava peli käyttää oikeaa ID:tä
                    boardgameRepository.save(editedBoardgame);  // Tallennetaan muokattu peli tietokantaan
                    return ResponseEntity.ok(editedBoardgame);  // Palautetaan 200 OK ja JSON-muotoinen olio
                })
                .orElseGet(() -> ResponseEntity.notFound().build());    // Jos peliä ei löydy, palautetaan 404 Not Found
    }

    // Poista lautapeli ID:n perusteella
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/api/boardgames/{id}")
    public ResponseEntity<Void> deleteBoardgame(@PathVariable("id") Long boardgameId) {
        if (boardgameRepository.existsById(boardgameId)) {      //tarkistetaan löytyykö ID:llä lautapeliä
            boardgameRepository.deleteById(boardgameId);        //poistetaan lautapeli ID:n perusteella
            return ResponseEntity.noContent().build(); // 204 No Content, onnistunut poisto
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found, peliä ei löydy
        }
    }
}
