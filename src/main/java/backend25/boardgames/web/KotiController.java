package backend25.boardgames.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import backend25.boardgames.domain.Boardgame;
import backend25.boardgames.domain.BoardgameRepository;

import java.util.List;

@Controller     // Spring MVC -kontrolleri, joka käsittelee web-pyyntöjä.
public class KotiController {

    private final BoardgameRepository boardgameRepository;

    public KotiController(BoardgameRepository boardgameRepository) {
        this.boardgameRepository = boardgameRepository;
    }

    @GetMapping({"/", "/koti"})
    public String koti(@RequestParam(value = "query", required = false) String query,Model model) {     // Käsittelee GET-pyynnön polkuun "/" tai "/koti", query(kysely)-parametri on valinnainen ja voi sisältää hakusanan
        model.addAttribute("message", "Tervetuloa lautapelikokoelman pariin!");     // Lisätään viesti näkymään
        model.addAttribute("description", "Täältä löydät lautapelejä moneen makuun.");      // Lisätään kuvausteksti näkymään

        List<Boardgame> games;
        if (query != null && !query.isBlank()) {
            games = boardgameRepository.findByTitleContainingIgnoreCase(query);     // Jos hakusana on annettu, haetaan pelit, joiden nimi sisältää hakusanan (ei kirjainkoolla väliä)
        } else {
            games = List.of();      // Muuten palautetaan tyhjä lista
        }

        model.addAttribute("games", games);     // Lisätään pelit näkymään
        return "koti";      // Palautetaan näkymä koti.html
    }
}