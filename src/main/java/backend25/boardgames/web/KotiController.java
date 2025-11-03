package backend25.boardgames.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;

import java.util.List;

@Controller
public class KotiController {

    private final BoardgameRepository boardgameRepository;

    public KotiController(BoardgameRepository boardgameRepository) {
        this.boardgameRepository = boardgameRepository;
    }

    @GetMapping({"/", "/koti"})
    public String koti(@RequestParam(value = "query", required = false) String query,Model model) {
        model.addAttribute("message", "Tervetuloa lautapelikokoelman pariin!");
        model.addAttribute("description", "Täältä löydät lautapelejä moneen makuun.");

        List<Boardgame> games;
        if (query != null && !query.isBlank()) {
            games = boardgameRepository.findByTitleContainingIgnoreCase(query);
        } else {
            games = List.of(); // tyhjä lista
        }

        model.addAttribute("games", games);
        return "koti";
    }
}