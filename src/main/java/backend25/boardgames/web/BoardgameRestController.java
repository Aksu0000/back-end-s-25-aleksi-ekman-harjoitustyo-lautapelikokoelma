package backend25.boardgames.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class BoardgameRestController {
    private final BoardgameRepository boardgameRepository;

	public BoardgameRestController(BoardgameRepository boardgameRepository) {
        this.boardgameRepository = boardgameRepository;
    }
    //hae kaikki lautapelit
    @GetMapping("/boardgames")
    public Iterable<Boardgame> getBoardgames() {
        return boardgameRepository.findAll();
    }

    //hae lautapeli id:n perusteella
    @GetMapping("/boardgames/{id}")
    public Boardgame getBoardgameById(@PathVariable("id") Long boardgameId) {
        return boardgameRepository.findById(boardgameId).orElse(null);
    }

    @PostMapping("/boardgames")
    public Boardgame addBoardgame(@RequestBody Boardgame newBoardgame) {
        return boardgameRepository.save(newBoardgame);
    }
    
    // päivitä olemassa oleva lautapeli JSON:illa
    @PutMapping("/boardgames/{id}")
    public ResponseEntity<Boardgame> editBoardgame(@RequestBody Boardgame editedBoardgame, @PathVariable Long id) {
        return boardgameRepository.findById(id)
                .map(boardgame -> {
                    editedBoardgame.setId(id);
                    boardgameRepository.save(editedBoardgame);
                    return ResponseEntity.ok(editedBoardgame);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Poista lautapeli ID:n perusteella
    @PreAuthorize("hasAuthorities('ADMIN')")
    @DeleteMapping("/boardgames/{id}")
    public ResponseEntity<Void> deleteBoardgame(@PathVariable("id") Long boardgameId) {
        if (boardgameRepository.existsById(boardgameId)) {
            boardgameRepository.deleteById(boardgameId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
